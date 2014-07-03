package edu.gslis.lucene.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import edu.gslis.lucene.indexer.Indexer;
import edu.gslis.lucene.main.config.QueryConfig;
import edu.gslis.lucene.main.config.QueryFile;
import edu.gslis.lucene.main.config.RunQueryConfig;


public class LuceneRunQuery {
    ClassLoader loader = ClassLoader.getSystemClassLoader();
    RunQueryConfig config;
    
    public LuceneRunQuery(RunQueryConfig config) {
        this.config = config;
    }
    
    public void run() throws Exception {
        String indexPath = config.getIndex();
        StopwordAnalyzerBase analyzer;
        String stopwordsPath = config.getStopwords();
        String analyzerClass = config.getAnalyzer();
        String docnoField = config.getDocno();
        if (StringUtils.isEmpty(docnoField))
            docnoField = "docno";
        
        if (!StringUtils.isEmpty(stopwordsPath))
        {
            @SuppressWarnings("rawtypes")
            Class analyzerCls = loader.loadClass(analyzerClass);
            @SuppressWarnings({ "rawtypes", "unchecked" })
            java.lang.reflect.Constructor analyzerConst = analyzerCls.getConstructor(Version.class, Reader.class);
            analyzer = (StopwordAnalyzerBase)analyzerConst.newInstance(Indexer.VERSION, new FileReader(stopwordsPath));            
        } else {
            @SuppressWarnings("rawtypes")
            Class analyzerCls = loader.loadClass(analyzerClass);
            @SuppressWarnings({ "rawtypes", "unchecked" })
            java.lang.reflect.Constructor analyzerConst = analyzerCls.getConstructor(Version.class, CharArraySet.class);
            analyzer = (StopwordAnalyzerBase)analyzerConst.newInstance(Indexer.VERSION, new CharArraySet(Indexer.VERSION, 0, true));           
        }
        
        Similarity similarity = new DefaultSimilarity();
        String similarityClass = config.getSimilarity();
        if (!StringUtils.isEmpty(similarityClass))
            similarity = (Similarity)loader.loadClass(similarityClass).newInstance();
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(similarity);
        String field = config.getField();
        if (StringUtils.isEmpty(field))
            field = "text";
        String[] fields = field.split(",");

        Set<QueryConfig> queries = config.getQueries();        
        if (queries == null)
            queries = new HashSet<QueryConfig>(); 
        QueryFile queryFile = config.getQueryFile();
        if (queryFile != null) {
            String path = queryFile.getPath();
            String format = queryFile.getFormat();
            if (format.startsWith("fedweb"))
                queries = readDelimitedFile(new File(path), format);
            else if (format.equals("indri")) {
                queries = readIndriQueries(new File(path));
            }
        }
        
        for (QueryConfig query: queries) {
            QueryParser parser = new MultiFieldQueryParser(Indexer.VERSION, fields, analyzer);
            Query q = parser.parse(query.getText());
            TopDocs topDocs = searcher.search(q, 1000);
            ScoreDoc[] docs = topDocs.scoreDocs;
            for (int i=0; i<docs.length; i++) {
                int docid = docs[i].doc;
                double score = docs[i].score;
                Document doc = searcher.doc(docid);
                
                String docno = doc.getField(docnoField).stringValue();
                long doclen = doc.getField(Indexer.FIELD_DOC_LEN).numericValue().longValue();

                System.out.println(query.getNumber() + " " + docno + " " + score + " " + doclen);
            }

        }
    }
    
    public static void main(String[] args) throws Exception {
        RunQueryConfig config = new RunQueryConfig();
        if (args.length == 0) 
        {
            System.err.println("you must specify a configuration file.");
            System.exit(-1);
        }
        else if (args.length == 1) {
            File yamlFile = new File(args[0]);
            if(!yamlFile.exists()) {
                System.err.println("Configuration file not found.");
                System.exit(-1);
            }
            Yaml yaml = new Yaml(new Constructor(RunQueryConfig.class));
            config = (RunQueryConfig)yaml.load(new FileInputStream(yamlFile));
        }        
        else {
            Options options = createOptions();
            CommandLineParser parser = new GnuParser();
            CommandLine cmd = parser.parse( options, args);
            
            String analyzer = cmd.getOptionValue("analyzer", Indexer.DEFAULT_ANALYZER);
            String docno = cmd.getOptionValue("docno", Indexer.FIELD_DOCNO);
            String field = cmd.getOptionValue("field", Indexer.FIELD_TEXT);
            String querynum = cmd.getOptionValue("querynum", "1");
            
            String similarity = cmd.getOptionValue("similarity", Indexer.DEFAULT_SIMILARITY);

            String query = cmd.getOptionValue("query");
            String queryfile = cmd.getOptionValue("queryfile");
            String format = cmd.getOptionValue("format");
            String index = cmd.getOptionValue("index");
            String stopwords = cmd.getOptionValue("stopwords");
            
            Set<QueryConfig> queries = new HashSet<QueryConfig>();                    
            if (!StringUtils.isEmpty(query)) {
                QueryConfig querycfg = new QueryConfig();
                querycfg.setNumber(querynum);
                querycfg.setText(query.replaceAll("'", "\""));
                queries.add(querycfg);
            } else if (!StringUtils.isEmpty(queryfile)) {
                if (format.startsWith("fedweb"))
                    queries = readDelimitedFile(new File(queryfile), format);
                else if (format.equals("indri")) {
                    queries = readIndriQueries(new File(queryfile));
                }
            }
            
            config.setAnalyzer(analyzer);
            config.setField(field);
            config.setIndex(index);
            config.setQueries(queries);
            config.setDocno(docno);
            config.setSimilarity(similarity);
            config.setStopwords(stopwords);
        }            
        LuceneRunQuery runner = new LuceneRunQuery(config);
        runner.run();
    }
    
    public static Set<QueryConfig> readDelimitedFile(File file, String format) throws Exception 
    {
        Set<QueryConfig> queries = new HashSet<QueryConfig>();

        List<String> rows = FileUtils.readLines(file);
        for (String row: rows) {
            if (format.equals("fedweb14")) {
                String[] fields = row.split("\t");
                QueryConfig q = new QueryConfig();
                q.setNumber(fields[0]);
                q.setText(fields[1]);
                queries.add(q);
            }
            else if (format.equals("fedweb13")) {
                String[] fields = row.split(":");
                QueryConfig q = new QueryConfig();
                q.setNumber(fields[0]);
                q.setText(fields[1]);
                queries.add(q);
            }
        }
        return queries;
    }
    
    public static Set<QueryConfig> readIndriQueries(File file) throws Exception 
    {
        Set<QueryConfig> queries = new HashSet<QueryConfig>();
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document xml = builder.parse(new FileInputStream(file));
        NodeList queryNodes = xml.getElementsByTagName("query");
        for (int i=0; i<queryNodes.getLength(); i++) {
            Element queryElem = (Element) queryNodes.item(i);
            QueryConfig query = new QueryConfig();
            
            String number = queryElem.getElementsByTagName("number").item(0).getFirstChild().getNodeValue();
            String text   = queryElem.getElementsByTagName("text").item(0).getFirstChild().getNodeValue();
            text = text.replaceAll("\\\n", "");
            query.setNumber(number);
            query.setText(text);
            queries.add(query);
        }
        return queries;
    }
    
    public static Options createOptions()
    {
        Options options = new Options();
        options.addOption("analyzer", true, "Analyzer class");
        options.addOption("index", true, "Path to index");
        options.addOption("field", true, "Field to search");
        options.addOption("docno", true, "Docno field");
        options.addOption("querynum", true, "Query identifier");
        options.addOption("query", true, "Query string");
        options.addOption("queryfile", true, "Query file");
        options.addOption("format", true, "Query file format");
        options.addOption("similarity", true, "Similarity class");
        options.addOption("stopwords", true, "Stopwords list");
        return options;
    }

}
