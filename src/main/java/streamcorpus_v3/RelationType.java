/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package streamcorpus_v3;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * RelationType is used in Relation to map relation "name" to type.
 * 
 * Relations 0 through 50 borrow from ACE with these string replacements:
 * s/-// and s/./_/
 * http://projects.ldc.upenn.edu/ace/docs/English-Events-Guidelines_v5.4.3.pdf
 * 
 * Relations 51-  borrows from KBP slot filling
 * http://surdeanu.info/kbp2013/TAC_2013_KBP_Slot_Descriptions_1.0.pdf
 * 
 * Most entity slots are relations, so the PER_ and ORG_ and FAC_
 * relations listed below are primary for slot filling.
 * 
 * Many of the KBP-based slots are redundant or overlapping with the
 * ACE-based slots.  The KBP-based slots are generally simpler and
 * were developed to support knowledge base population rather than
 * single-document extraction (as ACE was).  Therefore, for KB-focused
 * tasks, we recommend using the Relations 51-
 */
public enum RelationType implements org.apache.thrift.TEnum {
  PHYS_Located(0),
  PHYS_Near(1),
  PARTWHOLE_Geographical(2),
  PARTWHOLE_Subsidiary(3),
  PARTWHOLE_Artifact(4),
  PERSOC_Business(5),
  PERSOC_Family(6),
  PERSOC_LastingPersonal(7),
  ORGAFF_Employment(8),
  ORGAFF_Ownership(9),
  ORGAFF_Founder(10),
  ORGAFF_StudentAlum(11),
  ORGAFF_SportsAffiliation(12),
  ORGAFF_InvestorShareholder(13),
  ORGAFF_Membership(14),
  ART_UserOwnerInventorManufacturer(15),
  GENAFF_CitizenResidentReligionEthnicity(16),
  GENAFF_OrgLocation(17),
  Business_DeclareBankruptcy(18),
  Business_EndOrg(19),
  Business_MergeOrg(20),
  Business_StartOrg(21),
  Conflict_Attack(22),
  Conflict_Demonstrate(23),
  Contact_PhoneWrite(24),
  Contact_Meet(25),
  Justice_Acquit(26),
  Justice_Appeal(27),
  Justice_ArrestJail(28),
  Justice_ChargeIndict(29),
  Justice_Convict(30),
  Justice_Execute(31),
  Justice_Extradite(32),
  Justice_Fine(33),
  Justice_Pardon(34),
  Justice_ReleaseParole(35),
  Justice_Sentence(36),
  Justice_Sue(37),
  Justice_TrialHearing(38),
  Life_BeBorn(39),
  Life_Die(40),
  Life_Divorce(41),
  Life_Injure(42),
  Life_Marry(43),
  Movement_Transport(44),
  Personnel_Elect(45),
  Personnel_EndPosition(46),
  Personnel_Nominate(47),
  Personnel_StartPosition(48),
  Transaction_TransferMoney(49),
  Transaction_TransferOwnership(50),
  PER_DATE_OF_BIRTH(51),
  PER_COUNTRY_OF_BIRTH(52),
  PER_STATEORPROVINCE_OF_BIRTH(53),
  PER_CITY_OF_BIRTH(54),
  PER_ORIGIN(55),
  PER_DATE_OF_DEATH(56),
  PER_COUNTRY_OF_DEATH(57),
  PER_STATEORPROVINCE_OF_DEATH(58),
  PER_CITY_OF_DEATH(59),
  PER_COUNTRIES_OF_RESIDENCE(60),
  PER_STATESORPROVINCES_OF_RESIDENCE(61),
  PER_CITIES_OF_RESIDENCE(62),
  PER_SCHOOLS_ATTENDED(63),
  PER_EMPLOYEE_OR_MEMBER_OF(64),
  PER_RELIGION(65),
  PER_SPOUSE(66),
  PER_CHILDREN(67),
  PER_PARENTS(68),
  PER_SIBLINGS(69),
  PER_OTHER_FAMILY(70),
  ORG_TOP_MEMBERS_EMPLOYEES(71),
  ORG_MEMBERS(72),
  ORG_MEMBER_OF(73),
  ORG_SUBSIDIARIES(74),
  ORG_PARENTS(75),
  ORG_FOUNDED_BY(76),
  ORG_DATE_FOUNDED(77),
  ORG_DATE_DISSOLVED(78),
  ORG_COUNTRY_OF_HEADQUARTERS(79),
  ORG_STATEORPROVINCE_OF_HEADQUARTERS(80),
  ORG_CITY_OF_HEADQUARTERS(81),
  ORG_SHAREHOLDERS(82),
  ORG_POLITICAL_OR_RELIGIOUS_AFFILIATION(83),
  ORG_WEBSITE(84),
  FAC_LOCATED(85),
  FAC_VISITED_BY(86),
  FAC_OWNER(87),
  PER_WON_AWARD(88),
  PER_MET_WITH(89),
  PER_ATTENDED(90),
  PER_VISITED(91),
  ORG_ATTENDED(92),
  ORG_VISITED(93),
  PER_WEBSITE(94),
  PER_NATIONALITY(95);

  private final int value;

  private RelationType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static RelationType findByValue(int value) { 
    switch (value) {
      case 0:
        return PHYS_Located;
      case 1:
        return PHYS_Near;
      case 2:
        return PARTWHOLE_Geographical;
      case 3:
        return PARTWHOLE_Subsidiary;
      case 4:
        return PARTWHOLE_Artifact;
      case 5:
        return PERSOC_Business;
      case 6:
        return PERSOC_Family;
      case 7:
        return PERSOC_LastingPersonal;
      case 8:
        return ORGAFF_Employment;
      case 9:
        return ORGAFF_Ownership;
      case 10:
        return ORGAFF_Founder;
      case 11:
        return ORGAFF_StudentAlum;
      case 12:
        return ORGAFF_SportsAffiliation;
      case 13:
        return ORGAFF_InvestorShareholder;
      case 14:
        return ORGAFF_Membership;
      case 15:
        return ART_UserOwnerInventorManufacturer;
      case 16:
        return GENAFF_CitizenResidentReligionEthnicity;
      case 17:
        return GENAFF_OrgLocation;
      case 18:
        return Business_DeclareBankruptcy;
      case 19:
        return Business_EndOrg;
      case 20:
        return Business_MergeOrg;
      case 21:
        return Business_StartOrg;
      case 22:
        return Conflict_Attack;
      case 23:
        return Conflict_Demonstrate;
      case 24:
        return Contact_PhoneWrite;
      case 25:
        return Contact_Meet;
      case 26:
        return Justice_Acquit;
      case 27:
        return Justice_Appeal;
      case 28:
        return Justice_ArrestJail;
      case 29:
        return Justice_ChargeIndict;
      case 30:
        return Justice_Convict;
      case 31:
        return Justice_Execute;
      case 32:
        return Justice_Extradite;
      case 33:
        return Justice_Fine;
      case 34:
        return Justice_Pardon;
      case 35:
        return Justice_ReleaseParole;
      case 36:
        return Justice_Sentence;
      case 37:
        return Justice_Sue;
      case 38:
        return Justice_TrialHearing;
      case 39:
        return Life_BeBorn;
      case 40:
        return Life_Die;
      case 41:
        return Life_Divorce;
      case 42:
        return Life_Injure;
      case 43:
        return Life_Marry;
      case 44:
        return Movement_Transport;
      case 45:
        return Personnel_Elect;
      case 46:
        return Personnel_EndPosition;
      case 47:
        return Personnel_Nominate;
      case 48:
        return Personnel_StartPosition;
      case 49:
        return Transaction_TransferMoney;
      case 50:
        return Transaction_TransferOwnership;
      case 51:
        return PER_DATE_OF_BIRTH;
      case 52:
        return PER_COUNTRY_OF_BIRTH;
      case 53:
        return PER_STATEORPROVINCE_OF_BIRTH;
      case 54:
        return PER_CITY_OF_BIRTH;
      case 55:
        return PER_ORIGIN;
      case 56:
        return PER_DATE_OF_DEATH;
      case 57:
        return PER_COUNTRY_OF_DEATH;
      case 58:
        return PER_STATEORPROVINCE_OF_DEATH;
      case 59:
        return PER_CITY_OF_DEATH;
      case 60:
        return PER_COUNTRIES_OF_RESIDENCE;
      case 61:
        return PER_STATESORPROVINCES_OF_RESIDENCE;
      case 62:
        return PER_CITIES_OF_RESIDENCE;
      case 63:
        return PER_SCHOOLS_ATTENDED;
      case 64:
        return PER_EMPLOYEE_OR_MEMBER_OF;
      case 65:
        return PER_RELIGION;
      case 66:
        return PER_SPOUSE;
      case 67:
        return PER_CHILDREN;
      case 68:
        return PER_PARENTS;
      case 69:
        return PER_SIBLINGS;
      case 70:
        return PER_OTHER_FAMILY;
      case 71:
        return ORG_TOP_MEMBERS_EMPLOYEES;
      case 72:
        return ORG_MEMBERS;
      case 73:
        return ORG_MEMBER_OF;
      case 74:
        return ORG_SUBSIDIARIES;
      case 75:
        return ORG_PARENTS;
      case 76:
        return ORG_FOUNDED_BY;
      case 77:
        return ORG_DATE_FOUNDED;
      case 78:
        return ORG_DATE_DISSOLVED;
      case 79:
        return ORG_COUNTRY_OF_HEADQUARTERS;
      case 80:
        return ORG_STATEORPROVINCE_OF_HEADQUARTERS;
      case 81:
        return ORG_CITY_OF_HEADQUARTERS;
      case 82:
        return ORG_SHAREHOLDERS;
      case 83:
        return ORG_POLITICAL_OR_RELIGIOUS_AFFILIATION;
      case 84:
        return ORG_WEBSITE;
      case 85:
        return FAC_LOCATED;
      case 86:
        return FAC_VISITED_BY;
      case 87:
        return FAC_OWNER;
      case 88:
        return PER_WON_AWARD;
      case 89:
        return PER_MET_WITH;
      case 90:
        return PER_ATTENDED;
      case 91:
        return PER_VISITED;
      case 92:
        return ORG_ATTENDED;
      case 93:
        return ORG_VISITED;
      case 94:
        return PER_WEBSITE;
      case 95:
        return PER_NATIONALITY;
      default:
        return null;
    }
  }
}
