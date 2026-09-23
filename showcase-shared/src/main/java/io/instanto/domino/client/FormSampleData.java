// Sample objects generated from the pinned original form JSON; not a JSON runtime.
package io.instanto.domino.client;

import java.util.*;
import org.dominokit.domino.formsamples.shared.model.*;
import org.dominokit.domino.formsamples.shared.model.Country;

public final class FormSampleData {
  public static CorporateProfile profile() {
    return sample0();
  }

  public static List<Country> countries() {
    return new ArrayList<>(
        Arrays.asList(
            sample25(),
            sample26(),
            sample27(),
            sample28(),
            sample29(),
            sample30(),
            sample31(),
            sample32(),
            sample33(),
            sample34(),
            sample35(),
            sample36(),
            sample37(),
            sample38(),
            sample39(),
            sample40(),
            sample41(),
            sample42(),
            sample43(),
            sample44(),
            sample45(),
            sample46(),
            sample47(),
            sample48(),
            sample49(),
            sample50(),
            sample51(),
            sample52(),
            sample53(),
            sample54(),
            sample55(),
            sample56(),
            sample57(),
            sample58(),
            sample59(),
            sample60(),
            sample61(),
            sample62(),
            sample63(),
            sample64(),
            sample65(),
            sample66(),
            sample67(),
            sample68(),
            sample69(),
            sample70()));
  }

  public static List<Beneficiary> beneficiaries() {
    return new ArrayList<>(Arrays.asList(sample71()));
  }

  public static List<Bank> banks() {
    return new ArrayList<>(Arrays.asList(sample76()));
  }

  public static List<CurrencyData> currencies() {
    return new ArrayList<>(
        Arrays.asList(
            sample80(),
            sample81(),
            sample82(),
            sample83(),
            sample84(),
            sample85(),
            sample86(),
            sample87(),
            sample88(),
            sample89(),
            sample90(),
            sample91(),
            sample92(),
            sample93(),
            sample94(),
            sample95(),
            sample96(),
            sample97(),
            sample98(),
            sample99(),
            sample100(),
            sample101(),
            sample102(),
            sample103(),
            sample104(),
            sample105(),
            sample106(),
            sample107(),
            sample108(),
            sample109(),
            sample110(),
            sample111(),
            sample112(),
            sample113(),
            sample114(),
            sample115(),
            sample116(),
            sample117(),
            sample118(),
            sample119(),
            sample120(),
            sample121(),
            sample122(),
            sample123(),
            sample124(),
            sample125(),
            sample126(),
            sample127(),
            sample128(),
            sample129(),
            sample130(),
            sample131(),
            sample132(),
            sample133(),
            sample134(),
            sample135(),
            sample136(),
            sample137(),
            sample138(),
            sample139(),
            sample140(),
            sample141(),
            sample142(),
            sample143(),
            sample144(),
            sample145(),
            sample146(),
            sample147(),
            sample148(),
            sample149(),
            sample150(),
            sample151(),
            sample152(),
            sample153(),
            sample154(),
            sample155(),
            sample156(),
            sample157(),
            sample158(),
            sample159(),
            sample160(),
            sample161(),
            sample162(),
            sample163(),
            sample164(),
            sample165(),
            sample166(),
            sample167(),
            sample168(),
            sample169(),
            sample170(),
            sample171(),
            sample172(),
            sample173(),
            sample174(),
            sample175(),
            sample176(),
            sample177(),
            sample178(),
            sample179(),
            sample180(),
            sample181(),
            sample182(),
            sample183(),
            sample184(),
            sample185(),
            sample186(),
            sample187(),
            sample188(),
            sample189(),
            sample190(),
            sample191(),
            sample192(),
            sample193()));
  }

  private static Address sample1() {
    Address value = new Address();
    value.setId("1");
    value.setCountryISOCode("OM");
    value.setCity("Muscat");
    value.setStreet("street");
    value.setZipCode("MSS");
    value.setApartment("apartment");
    value.setMailBox("mailBox");
    value.setPhoneNumber("1234567");
    value.setFaxNumber("12345678");
    return value;
  }

  private static CurrencyData sample3() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample2() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("OUT");
    value.setAccountType("SAVING");
    value.setCurrency(sample3());
    value.setIban("AB89ABCD0000000000009235351490");
    value.setAccountAlias("LG.Inc");
    value.setAccountNumber("0000000000009235351490");
    return value;
  }

  private static CurrencyData sample5() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample4() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("IN");
    value.setAccountType("SAVING");
    value.setCurrency(sample5());
    value.setIban("AB62ABCD0000000000009235351491");
    value.setAccountAlias("Hewlett-Packard");
    value.setAccountNumber("0000000000009235351491");
    return value;
  }

  private static CurrencyData sample7() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample6() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("OUT");
    value.setAccountType("SAVING");
    value.setCurrency(sample7());
    value.setIban("AB35ABCD0000000000009235351492");
    value.setAccountAlias("Progressoft");
    value.setAccountNumber("0000000000009235351492");
    return value;
  }

  private static CurrencyData sample9() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample8() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("IN_OUT");
    value.setAccountType("SAVING");
    value.setCurrency(sample9());
    value.setIban("AB08ABCD0000000000009235351493");
    value.setAccountAlias("Netflix");
    value.setAccountNumber("0000000000009235351493");
    return value;
  }

  private static CurrencyData sample11() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample10() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("OUT");
    value.setAccountType("SAVING");
    value.setCurrency(sample11());
    value.setIban("AB78ABCD0000000000009235351494");
    value.setAccountAlias("Amazon");
    value.setAccountNumber("0000000000009235351494");
    return value;
  }

  private static CurrencyData sample13() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample12() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("IN");
    value.setAccountType("SAVING");
    value.setCurrency(sample13());
    value.setIban("AB51ABCD0000000000009235351495");
    value.setAccountAlias("Google");
    value.setAccountNumber("0000000000009235351495");
    return value;
  }

  private static CurrencyData sample15() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample14() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("IN");
    value.setAccountType("SAVING");
    value.setCurrency(sample15());
    value.setIban("AB24ABCD0000000000009235351496");
    value.setAccountAlias("Versend");
    value.setAccountNumber("0000000000009235351496");
    return value;
  }

  private static CurrencyData sample17() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDefaultFractionDigits(3);
    value.setNumericCode(414);
    return value;
  }

  private static CorporateAccount sample16() {
    CorporateAccount value = new CorporateAccount();
    value.setAccountAccessibility("IN_OUT");
    value.setAccountType("SAVING");
    value.setCurrency(sample17());
    value.setIban("AB94ABCD0000000000009235351497");
    value.setAccountAlias("Aramex");
    value.setAccountNumber("0000000000009235351497");
    return value;
  }

  private static Address sample20() {
    Address value = new Address();
    value.setId("2");
    value.setCountryISOCode("OM");
    value.setCity("Muscat");
    value.setStreet("street");
    value.setZipCode("MSS");
    value.setApartment("apartment");
    value.setMailBox("mailBox");
    value.setPhoneNumber("1235898");
    value.setFaxNumber("36465141");
    return value;
  }

  private static ContactPerson sample21() {
    ContactPerson value = new ContactPerson();
    value.setName("Hadil-Muscat");
    value.setMobileNumber("9843358717");
    value.setEmail("noman.mail@compay.com");
    return value;
  }

  private static Branch sample19() {
    Branch value = new Branch();
    value.setName("Muscat");
    value.setAddress(sample20());
    value.setContactPerson(sample21());
    return value;
  }

  private static Address sample23() {
    Address value = new Address();
    value.setId("1");
    value.setCountryISOCode("OM");
    value.setCity("Muscat");
    value.setStreet("street");
    value.setZipCode("MSS");
    value.setApartment("apartment");
    value.setMailBox("mailBox");
    value.setPhoneNumber("1235898");
    value.setFaxNumber("36465141");
    return value;
  }

  private static ContactPerson sample24() {
    ContactPerson value = new ContactPerson();
    value.setName("Hadil");
    value.setMobileNumber("9843358716");
    value.setEmail("noman@company.com");
    return value;
  }

  private static Branch sample22() {
    Branch value = new Branch();
    value.setName("HeadQuater");
    value.setAddress(sample23());
    value.setContactPerson(sample24());
    return value;
  }

  private static Bank sample18() {
    Bank value = new Bank();
    value.setName("Bank ABC");
    value.setSwiftCode("ABCDEFGHXXX");
    value.setShortName("S.A.O.J");
    value.setBranches(new ArrayList<>(Arrays.asList(sample19(), sample22())));
    return value;
  }

  private static CorporateProfile sample0() {
    CorporateProfile value = new CorporateProfile();
    value.setId("1");
    value.setName("Progressoft corp.");
    value.setCode("PSF");
    value.setReference("PSFT");
    value.setAddress(sample1());
    value.setCorporateAccounts(
        new ArrayList<>(
            Arrays.asList(
                sample2(),
                sample4(),
                sample6(),
                sample8(),
                sample10(),
                sample12(),
                sample14(),
                sample16())));
    value.setBanks(new ArrayList<>(Arrays.asList(sample18())));
    return value;
  }

  private static Country sample25() {
    Country value = new Country();
    value.setIso("AND");
    value.setCode("AD");
    value.setName("Andorra");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Andorra la Vella",
                "Canillo",
                "Encamp",
                "La Massana",
                "Escaldes-Engordany",
                "Ordino",
                "Sant Julia de Loria")));
    return value;
  }

  private static Country sample26() {
    Country value = new Country();
    value.setIso("ARE");
    value.setCode("AE");
    value.setName("United Arab Emirates");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Abu Dhabi",
                "\u0027Ajman",
                "Al Fujayrah",
                "Ash Shariqah (Sharjah)",
                "Dubayy (Dubai)",
                "Ra\u0027s al Khaymah",
                "Umm al Qaywayn")));
    return value;
  }

  private static Country sample27() {
    Country value = new Country();
    value.setIso("AFG");
    value.setCode("AF");
    value.setName("Afghanistan");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Kabul",
                "Badakhshan",
                "Badghis",
                "Baghlan",
                "Balkh",
                "Bamian",
                "Farah",
                "Faryab",
                "Ghazni",
                "Ghowr",
                "Helmand",
                "Herat",
                "Jowzjan",
                "Kabol",
                "Kandahar",
                "Kapisa",
                "Khowst",
                "Konar",
                "Kondoz",
                "Laghman",
                "Lowgar",
                "Nangarhar",
                "Nimruz",
                "Nurestan",
                "Oruzgan",
                "Paktia",
                "Paktika",
                "Parvan",
                "Samangan",
                "Sar-e Pol",
                "Takhar",
                "Vardak",
                "Zabol")));
    return value;
  }

  private static Country sample28() {
    Country value = new Country();
    value.setIso("AIA");
    value.setCode("AI");
    value.setName("Anguilla");
    value.setCities(new ArrayList<>(Arrays.asList("The Valley")));
    return value;
  }

  private static Country sample29() {
    Country value = new Country();
    value.setIso("ALB");
    value.setCode("AL");
    value.setName("Albania");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Tirana",
                "Berat",
                "Bulqize",
                "Delvine",
                "Devoll",
                "Diber",
                "Durres",
                "Elbasan",
                "Fier",
                "Gjirokaster",
                "Gramsh",
                "Has",
                "Kavaje",
                "Kolonje",
                "Korce",
                "Kruje",
                "Kucove",
                "Kukes",
                "Kurbin",
                "Lezhe",
                "Librazhd",
                "Lushnje",
                "Malesi e Madhe",
                "Mallakaster",
                "Mat",
                "Mirdite",
                "Peqin",
                "Permet",
                "Pogradec",
                "Puke",
                "Sarande",
                "Shkoder",
                "Skrapar",
                "Tepelene",
                "Tirane",
                "Tropoje",
                "Vlore")));
    return value;
  }

  private static Country sample30() {
    Country value = new Country();
    value.setIso("ARM");
    value.setCode("AM");
    value.setName("Armenia");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Yerevan",
                "Aragatsotn",
                "Ararat",
                "Armavir",
                "Geghark\u0027unik\u0027",
                "Kotayk\u0027",
                "Lorri",
                "Shirak",
                "Syunik\u0027",
                "Tavush",
                "Vayots\u0027 Dzor")));
    return value;
  }

  private static Country sample31() {
    Country value = new Country();
    value.setIso("ANT");
    value.setCode("AN");
    value.setName("Netherlands Antilles");
    value.setCities(new ArrayList<>(Arrays.asList("Willemstad")));
    return value;
  }

  private static Country sample32() {
    Country value = new Country();
    value.setIso("AGO");
    value.setCode("AO");
    value.setName("Angola");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Luanda",
                "Bengo",
                "Benguela",
                "Bie",
                "Cabinda",
                "Cuando Cubango",
                "Cuanza Norte",
                "Cuanza Sul",
                "Cunene",
                "Huambo",
                "Huila",
                "Lunda Norte",
                "Lunda Sul",
                "Malanje",
                "Moxico",
                "Namibe",
                "Uige",
                "Zaire")));
    return value;
  }

  private static Country sample33() {
    Country value = new Country();
    value.setIso("ARG");
    value.setCode("AR");
    value.setName("Argentina");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Buenos Aires",
                "Catamarca",
                "Chaco",
                "Chubut",
                "Cordoba",
                "Corrientes",
                "Entre Rios",
                "Formosa",
                "Jujuy",
                "La Pampa",
                "La Rioja",
                "Mendoza",
                "Misiones",
                "Neuquen",
                "Rio Negro",
                "Salta",
                "San Juan",
                "San Luis",
                "Santa Cruz",
                "Santa Fe",
                "Santiago del Estero",
                "Tucuman")));
    return value;
  }

  private static Country sample34() {
    Country value = new Country();
    value.setIso("AUT");
    value.setCode("AT");
    value.setName("Austria");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Vienna",
                "Burgenland",
                "Kaernten",
                "Niederoesterreich",
                "Oberoesterreich",
                "Salzburg",
                "Steiermark",
                "Tirol",
                "Vorarlberg",
                "Wien")));
    return value;
  }

  private static Country sample35() {
    Country value = new Country();
    value.setIso("AUS");
    value.setCode("AU");
    value.setName("Australia");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Canberra",
                "Australian Capital Territory",
                "New South Wales",
                "Northern Territory",
                "Queensland",
                "South Australia",
                "Tasmania",
                "Victoria",
                "Western Australia")));
    return value;
  }

  private static Country sample36() {
    Country value = new Country();
    value.setIso("ABW");
    value.setCode("AW");
    value.setName("Aruba");
    value.setCities(new ArrayList<>(Arrays.asList("Oranjestad")));
    return value;
  }

  private static Country sample37() {
    Country value = new Country();
    value.setIso("AZE");
    value.setCode("AZ");
    value.setName("Azerbaijan");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Baku (Baki)",
                "Abseron",
                "Agcabadi",
                "Agdam",
                "Agdas",
                "Agstafa",
                "Agsu",
                "Ali Bayramli",
                "Astara",
                "Balakan",
                "Barda",
                "Beylaqan",
                "Bilasuvar",
                "Cabrayil",
                "Calilabad",
                "Daskasan",
                "Davaci",
                "Fuzuli",
                "Gadabay",
                "Ganca",
                "Goranboy",
                "Goycay",
                "Haciqabul",
                "Imisli",
                "Ismayilli",
                "Kalbacar",
                "Kurdamir",
                "Lacin",
                "Lankaran",
                "Lankaran",
                "Lerik",
                "Masalli",
                "Mingacevir",
                "Naftalan",
                "Naxcivan",
                "Neftcala",
                "Oguz",
                "Qabala",
                "Qax",
                "Qazax",
                "Qobustan",
                "Quba",
                "Qubadli",
                "Qusar",
                "Saatli",
                "Sabirabad",
                "Saki",
                "Saki",
                "Salyan",
                "Samaxi",
                "Samkir",
                "Samux",
                "Siyazan",
                "Sumqayit",
                "Susa",
                "Susa",
                "Tartar",
                "Tovuz",
                "Ucar",
                "Xacmaz",
                "Xankandi",
                "Xanlar",
                "Xizi",
                "Xocali",
                "Xocavand",
                "Yardimli",
                "Yevlax",
                "Yevlax",
                "Zangilan",
                "Zaqatala",
                "Zardab")));
    return value;
  }

  private static Country sample38() {
    Country value = new Country();
    value.setIso("BRB");
    value.setCode("BB");
    value.setName("Barbados");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Bridgetown",
                "Christ Church",
                "Saint Andrew",
                "Saint George",
                "Saint James",
                "Saint John",
                "Saint Joseph",
                "Saint Lucy",
                "Saint Michael",
                "Saint Peter",
                "Saint Philip",
                "Saint Thomas")));
    return value;
  }

  private static Country sample39() {
    Country value = new Country();
    value.setIso("BGD");
    value.setCode("BD");
    value.setName("Bangladesh");
    value.setCities(
        new ArrayList<>(
            Arrays.asList("Dhaka", "Barisal", "Chittagong", "Khulna", "Rajshahi", "Sylhet")));
    return value;
  }

  private static Country sample40() {
    Country value = new Country();
    value.setIso("BEL");
    value.setCode("BE");
    value.setName("Belgium");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Brussels",
                "Antwerpen",
                "Brabant Wallon",
                "Brussels (Bruxelles)",
                "Hainaut",
                "Liege",
                "Limburg",
                "Luxembourg",
                "Namur",
                "Oost-Vlaanderen",
                "Vlaams-Brabant",
                "West-Vlaanderen")));
    return value;
  }

  private static Country sample41() {
    Country value = new Country();
    value.setIso("BFA");
    value.setCode("BF");
    value.setName("Burkina Faso");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Ouagadougou",
                "Bale",
                "Bam",
                "Banwa",
                "Bazega",
                "Bougouriba",
                "Boulgou",
                "Boulkiemde",
                "Comoe",
                "Ganzourgou",
                "Gnagna",
                "Gourma",
                "Houet",
                "Ioba",
                "Kadiogo",
                "Kenedougou",
                "Komandjari",
                "Kompienga",
                "Kossi",
                "Koupelogo",
                "Kouritenga",
                "Kourweogo",
                "Leraba",
                "Loroum",
                "Mouhoun",
                "Nahouri",
                "Namentenga",
                "Nayala",
                "Naumbiel",
                "Oubritenga",
                "Oudalan",
                "Passore",
                "Poni",
                "Samentenga",
                "Sanguie",
                "Seno",
                "Sissili",
                "Soum",
                "Sourou",
                "Tapoa",
                "Tuy",
                "Yagha",
                "Yatenga",
                "Ziro",
                "Zondomo",
                "Zoundweogo")));
    return value;
  }

  private static Country sample42() {
    Country value = new Country();
    value.setIso("BGR");
    value.setCode("BG");
    value.setName("Bulgaria");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Sofiya",
                "Blagoevgrad",
                "Burgas",
                "Dobrich",
                "Gabrovo",
                "Khaskovo",
                "Kurdzhali",
                "Kyustendil",
                "Lovech",
                "Montana",
                "Pazardzhik",
                "Pernik",
                "Pleven",
                "Plovdiv",
                "Razgrad",
                "Ruse",
                "Shumen",
                "Silistra",
                "Sliven",
                "Smolyan",
                "Sofiya-Grad",
                "Stara Zagora",
                "Turgovishte",
                "Varna",
                "Veliko Turnovo",
                "Vidin",
                "Vratsa",
                "Yambol")));
    return value;
  }

  private static Country sample43() {
    Country value = new Country();
    value.setIso("BHR");
    value.setCode("BH");
    value.setName("Bahrain");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Manama",
                "Al Hadd",
                "Al Manamah",
                "Al Mintaqah al Gharbiyah",
                "Al Mintaqah al Wusta",
                "Al Mintaqah ash Shamaliyah",
                "Al Muharraq",
                "Ar Rifa\u0027 wa al Mintaqah al Janubiyah",
                "Jidd Hafs",
                "Madinat Hamad",
                "Madinat \u0027Isa",
                "Juzur Hawar",
                "Sitrah")));
    return value;
  }

  private static Country sample44() {
    Country value = new Country();
    value.setIso("BDI");
    value.setCode("BI");
    value.setName("Burundi");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Bujumbura",
                "Bubanza",
                "Bujumbura",
                "Bururi",
                "Cankuzo",
                "Cibitoke",
                "Gitega",
                "Karuzi",
                "Kayanza",
                "Kirundo",
                "Makamba",
                "Muramvya",
                "Muyinga",
                "Mwaro",
                "Ngozi",
                "Rutana",
                "Ruyigi")));
    return value;
  }

  private static Country sample45() {
    Country value = new Country();
    value.setIso("BEN");
    value.setCode("BJ");
    value.setName("Benin");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Porto-Novo",
                "Alibori",
                "Atakora",
                "Atlantique",
                "Borgou",
                "Collines",
                "Couffo",
                "Donga",
                "Littoral",
                "Mono",
                "Oueme",
                "Plateau",
                "Zou")));
    return value;
  }

  private static Country sample46() {
    Country value = new Country();
    value.setIso("BMU");
    value.setCode("BM");
    value.setName("Bermuda");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Hamilton",
                "Devonshire",
                "Hamilton",
                "Hamilton",
                "Paget",
                "Pembroke",
                "Saint George",
                "Saint George\u0027s",
                "Sandys",
                "Smith\u0027s",
                "Southampton",
                "Warwick")));
    return value;
  }

  private static Country sample47() {
    Country value = new Country();
    value.setIso("BRN");
    value.setCode("BN");
    value.setName("Brunei");
    value.setCities(
        new ArrayList<>(
            Arrays.asList("Bandar Seri Begawan", "Belait", "Brunei/Muara", "Temburong", "Tutong")));
    return value;
  }

  private static Country sample48() {
    Country value = new Country();
    value.setIso("BOL");
    value.setCode("BO");
    value.setName("Bolivia");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "La Paz",
                "Sucre",
                "Chuquisaca",
                "Cochabamba",
                "Beni",
                "Oruro",
                "Pando",
                "Potosi",
                "Santa Cruz",
                "Tarija")));
    return value;
  }

  private static Country sample49() {
    Country value = new Country();
    value.setIso("BRA");
    value.setCode("BR");
    value.setName("Brazil");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Brasilia",
                "Acre",
                "Alagoas",
                "Amapa",
                "Amazonas",
                "Bahia",
                "Ceara",
                "Distrito Federal",
                "Espirito Santo",
                "Goias",
                "Maranhao",
                "Mato Grosso",
                "Mato Grosso do Sul",
                "Minas Gerais",
                "Para",
                "Paraiba",
                "Parana",
                "Pernambuco",
                "Piaui",
                "Rio de Janeiro",
                "Rio Grande do Norte",
                "Rio Grande do Sul",
                "Rondonia",
                "Roraima",
                "Santa Catarina",
                "Sao Paulo",
                "Sergipe",
                "Tocantins")));
    return value;
  }

  private static Country sample50() {
    Country value = new Country();
    value.setIso("BHS");
    value.setCode("BS");
    value.setName("Bahamas");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Nassau",
                "Acklins/Crooked Islands",
                "Bimini",
                "Cat Island",
                "Exuma",
                "Freeport",
                "Fresh Creek",
                "Governor\u0027s Harbour",
                "Green Turtle Cay",
                "Harbour Island",
                "High Rock",
                "Inagua",
                "Kemps Bay",
                "Long Island",
                "Marsh Harbour",
                "Mayaguana",
                "New Providence",
                "Nichollstown/Berry Islands",
                "Ragged Island",
                "Rock Sound",
                "Sandy Point",
                "San Salvador/Rum Cay")));
    return value;
  }

  private static Country sample51() {
    Country value = new Country();
    value.setIso("BTN");
    value.setCode("BT");
    value.setName("Bhutan");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Thimphu",
                "Bumthang",
                "Chhukha",
                "Chirang",
                "Dagana",
                "Gasa",
                "Geylegphug",
                "Ha",
                "Lhuntshi",
                "Mongar",
                "Paro",
                "Pemagatsel",
                "Punakha",
                "Samchi",
                "Samdrup Jongkhar",
                "Shemgang",
                "Tashigang",
                "Tongsa",
                "Wangdi Phodrang",
                "Yangtse")));
    return value;
  }

  private static Country sample52() {
    Country value = new Country();
    value.setIso("BWA");
    value.setCode("BW");
    value.setName("Botswana");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Gaborone",
                "Central",
                "Chobe",
                "Francistown",
                "Ghanzi",
                "Kgalagadi",
                "Kgatleng",
                "Kweneng",
                "Lobatse",
                "Ngamiland",
                "North-East",
                "Selebi-Pikwe",
                "South-East",
                "Southern")));
    return value;
  }

  private static Country sample53() {
    Country value = new Country();
    value.setIso("BLR");
    value.setCode("BY");
    value.setName("Belarus");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Minsk",
                "Brest",
                "Homyel\u0027",
                "Horad Minsk",
                "Hrodna",
                "Mahilyow",
                "Vitsyebsk")));
    return value;
  }

  private static Country sample54() {
    Country value = new Country();
    value.setIso("BLZ");
    value.setCode("BZ");
    value.setName("Belize");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Belmopan", "Belize", "Cayo", "Corozal", "Orange Walk", "Stann Creek", "Toledo")));
    return value;
  }

  private static Country sample55() {
    Country value = new Country();
    value.setIso("CAN");
    value.setCode("CA");
    value.setName("Canada");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Ottawa",
                "Alberta",
                "British Columbia",
                "Manitoba",
                "New Brunswick",
                "Newfoundland and Labrador",
                "Northwest Territories",
                "Nova Scotia",
                "Nunavut",
                "Ontario",
                "Prince Edward Island",
                "Quebec",
                "Saskatchewan",
                "Yukon Territory")));
    return value;
  }

  private static Country sample56() {
    Country value = new Country();
    value.setIso("CAF");
    value.setCode("CF");
    value.setName("Central African Republic");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Bangui",
                "Bamingui-Bangoran",
                "Basse-Kotto",
                "Gribingui",
                "Haute-Kotto",
                "Haute-Sangha",
                "Haut-Mbomou",
                "Kemo-Gribingui",
                "Lobaye",
                "Mbomou",
                "Nana-Mambere",
                "Ombella-Mpoko",
                "Ouaka",
                "Ouham",
                "Ouham-Pende",
                "Sangha",
                "Vakaga")));
    return value;
  }

  private static Country sample57() {
    Country value = new Country();
    value.setIso("CHE");
    value.setCode("CH");
    value.setName("Switzerland");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Bern",
                "Aargau",
                "Appenzell Ausser-Rhoden",
                "Appenzell Inner-Rhoden",
                "Basel-Landschaft",
                "Basel-Stadt",
                "Fribourg",
                "Geneve",
                "Glarus",
                "Graubunden",
                "Jura",
                "Luzern",
                "Neuchatel",
                "Nidwalden",
                "Obwalden",
                "Sankt Gallen",
                "Schaffhausen",
                "Schwyz",
                "Solothurn",
                "Thurgau",
                "Ticino",
                "Uri",
                "Valais",
                "Vaud",
                "Zug",
                "Zurich")));
    return value;
  }

  private static Country sample58() {
    Country value = new Country();
    value.setIso("CHL");
    value.setCode("CL");
    value.setName("Chile");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Santiago",
                "Antofagasta",
                "Araucania",
                "Atacama",
                "Bio-Bio",
                "Coquimbo",
                "Los Lagos",
                "Maule",
                "Tarapaca",
                "Valparaiso")));
    return value;
  }

  private static Country sample59() {
    Country value = new Country();
    value.setIso("CMR");
    value.setCode("CM");
    value.setName("Cameroon");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Yaounde",
                "Adamaoua",
                "Centre",
                "Est",
                "Extreme-Nord",
                "Littoral",
                "Nord",
                "Nord-Ouest",
                "Ouest",
                "Sud",
                "Sud-Ouest")));
    return value;
  }

  private static Country sample60() {
    Country value = new Country();
    value.setIso("CHN");
    value.setCode("CN");
    value.setName("China");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Beijing",
                "Anhui",
                "Chongqing",
                "Fujian",
                "Gansu",
                "Guangdong",
                "Guangxi",
                "Guizhou",
                "Hainan",
                "Hebei",
                "Heilongjiang",
                "Henan",
                "Hubei",
                "Hunan",
                "Jiangsu",
                "Jiangxi",
                "Jilin",
                "Liaoning",
                "Nei Mongol",
                "Ningxia",
                "Qinghai",
                "Shaanxi",
                "Shandong",
                "Shanghai",
                "Shanxi",
                "Sichuan",
                "Tianjin",
                "Xinjiang",
                "Xizang (Tibet)",
                "Yunnan",
                "Zhejiang")));
    return value;
  }

  private static Country sample61() {
    Country value = new Country();
    value.setIso("COL");
    value.setCode("CO");
    value.setName("Colombia");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Bogota",
                "Amazonas",
                "Antioquia",
                "Arauca",
                "Atlantico",
                "Bolivar",
                "Boyaca",
                "Caldas",
                "Caqueta",
                "Casanare",
                "Cauca",
                "Cesar",
                "Choco",
                "Cordoba",
                "Cundinamarca",
                "Guainia",
                "Guaviare",
                "Huila",
                "La Guajira",
                "Magdalena",
                "Meta",
                "Narino",
                "Norte de Santander",
                "Putumayo",
                "Quindio",
                "Risaralda",
                "San Andres/Providencia",
                "Santander",
                "Sucre",
                "Tolima",
                "Valle del Cauca",
                "Vaupes",
                "Vichada")));
    return value;
  }

  private static Country sample62() {
    Country value = new Country();
    value.setIso("CRI");
    value.setCode("CR");
    value.setName("Costa Rica");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "San Jose",
                "Alajuela",
                "Cartago",
                "Guanacaste",
                "Heredia",
                "Limon",
                "Puntarenas")));
    return value;
  }

  private static Country sample63() {
    Country value = new Country();
    value.setIso("CUB");
    value.setCode("CU");
    value.setName("Cuba");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Havana",
                "Camaguey",
                "Ciego de Avila",
                "Cienfuegos",
                "Ciudad de La Habana",
                "Granma",
                "Guantanamo",
                "Holguin",
                "Isla de la Juventud",
                "La Habana",
                "Las Tunas",
                "Matanzas",
                "Pinar del Rio",
                "Sancti Spiritus",
                "Santiago de Cuba",
                "Villa Clara")));
    return value;
  }

  private static Country sample64() {
    Country value = new Country();
    value.setIso("CPV");
    value.setCode("CV");
    value.setName("Cape Verde");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Praia",
                "Boa Vista",
                "Brava",
                "Calheta",
                "Maio",
                "Mosteiros",
                "Paul",
                "Porto Novo",
                "Ribeira Grande",
                "Sal",
                "Santa Catarina",
                "Santa Cruz",
                "Sao Domingos",
                "Sao Nicolau",
                "Sao Filipe",
                "Sao Vicente",
                "Tarrafal")));
    return value;
  }

  private static Country sample65() {
    Country value = new Country();
    value.setIso("CYP");
    value.setCode("CY");
    value.setName("Cyprus");
    value.setCities(
        new ArrayList<>(
            Arrays.asList("Nicosia", "Famagusta", "Kyrenia", "Larnaca", "Limassol", "Paphos")));
    return value;
  }

  private static Country sample66() {
    Country value = new Country();
    value.setIso("CZE");
    value.setCode("CZ");
    value.setName("Czech Republic");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Prague (Praha)",
                "Jihocesky",
                "Jihomoravsky",
                "Karlovarsky",
                "Kralovehradecky",
                "Liberecky",
                "Moravskoslezsky",
                "Olomoucky",
                "Pardubicky",
                "Plzensky",
                "Stredocesky",
                "Ustecky",
                "Vysocina",
                "Zlinsky")));
    return value;
  }

  private static Country sample67() {
    Country value = new Country();
    value.setIso("DEU");
    value.setCode("DE");
    value.setName("Germany");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Berlin",
                "Baden-Wuerttemberg",
                "Bayern",
                "Berlin",
                "Brandenburg",
                "Bremen",
                "Hamburg",
                "Hessen",
                "Mecklenburg-Vorpommern",
                "Niedersachsen",
                "Nordrhein-Westfalen",
                "Rheinland-Pfalz",
                "Saarland",
                "Sachsen",
                "Sachsen-Anhalt",
                "Schleswig-Holstein",
                "Thueringen")));
    return value;
  }

  private static Country sample68() {
    Country value = new Country();
    value.setIso("DJI");
    value.setCode("DJ");
    value.setName("Djibouti");
    value.setCities(
        new ArrayList<>(
            Arrays.asList("Djibouti", "\u0027Ali Sabih", "Dikhil", "Obock", "Tadjoura")));
    return value;
  }

  private static Country sample69() {
    Country value = new Country();
    value.setIso("DNK");
    value.setCode("DK");
    value.setName("Denmark");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Copenhagen (Kobenhavn)",
                "Arhus",
                "Bornholm",
                "Fredericksberg",
                "Frederiksborg",
                "Fyn",
                "Kobenhavns",
                "Nordjylland",
                "Ribe",
                "Ringkobing",
                "Roskilde",
                "Sonderjylland",
                "Storstrom",
                "Vejle",
                "Vestsjalland",
                "Viborg")));
    return value;
  }

  private static Country sample70() {
    Country value = new Country();
    value.setIso("DMA");
    value.setCode("DM");
    value.setName("Dominica");
    value.setCities(
        new ArrayList<>(
            Arrays.asList(
                "Roseau",
                "Saint Andrew",
                "Saint David",
                "Saint George",
                "Saint John",
                "Saint Joseph",
                "Saint Luke",
                "Saint Mark",
                "Saint Patrick",
                "Saint Paul",
                "Saint Peter")));
    return value;
  }

  private static Address sample72() {
    Address value = new Address();
    value.setId("4658f7d3-b94c-4875-af45-41fc41b774e8");
    value.setCountryISOCode("JOR");
    value.setCity("Amman");
    return value;
  }

  private static ContactPerson sample73() {
    ContactPerson value = new ContactPerson();
    value.setId("1d8577a5-e332-498a-b1fc-11ef936dc997");
    value.setName("Noman person");
    value.setContactNumber("07896654545");
    return value;
  }

  private static Account sample74() {
    Account value = new Account();
    value.setId("1");
    value.setIban("AB89ABCD0000000000009235351490");
    value.setAccountAlias("current account");
    value.setAccountNumber("009235351490");
    value.setCountry("Kuwait");
    value.setBank("Bank XYZ");
    value.setBicCode("XYZ");
    value.setCurrency("JOD");
    value.setAccountState("ACTIVE");
    return value;
  }

  private static Account sample75() {
    Account value = new Account();
    value.setId("2");
    value.setIban("AB62ABCD0000000000009235351491");
    value.setAccountAlias("main account");
    value.setAccountNumber("123456789123");
    value.setCountry("Jordan");
    value.setBank("Bank CBA");
    value.setBicCode("CBA");
    value.setCurrency("JOD");
    value.setAccountState("ACTIVE");
    return value;
  }

  private static Beneficiary sample71() {
    Beneficiary value = new Beneficiary();
    value.setId("71328a43-d119-4e99-8bd0-629c9fa39415");
    value.setProcessInstanceId("39");
    value.setTenantId("corpay");
    value.setCreatedDate("1532217600000");
    value.setCreatedBy("1");
    value.setName("Corp. ABC");
    value.setProfileType("bank");
    value.setAddress(sample72());
    value.setContactPerson(sample73());
    value.setStartingDate("1532217600000");
    value.setExpiryDate("1847836800000");
    value.setState("ACTIVE");
    value.setAccounts(new ArrayList<>(Arrays.asList(sample74(), sample75())));
    value.setAgreements(new ArrayList<>(Arrays.asList()));
    return value;
  }

  private static Address sample77() {
    Address value = new Address();
    value.setCountryISOCode("OM");
    value.setCity("City Y");
    value.setStreet("Street ABC");
    value.setApartment("Building XYZ");
    value.setZipCode("123");
    value.setMailBox("PO Box 123");
    value.setPhoneNumber("+900 12345678");
    value.setFaxNumber("+900 12345678");
    return value;
  }

  private static Address sample79() {
    Address value = new Address();
    value.setCountryISOCode("OM");
    value.setCity("City x");
    value.setStreet("ABC Street");
    value.setApartment("Building XYZ");
    value.setZipCode("123");
    value.setMailBox("PO Box 123");
    value.setPhoneNumber("+900 12345678");
    value.setFaxNumber("+900 12345678");
    return value;
  }

  private static ContactPerson sample78() {
    ContactPerson value = new ContactPerson();
    value.setName("\tMr Noman person");
    value.setContactNumber("+900 12345678");
    value.setEmail("xyz@abcdefghi.com");
    value.setAddress(sample79());
    return value;
  }

  private static Bank sample76() {
    Bank value = new Bank();
    value.setName("Bank XYZ");
    value.setSwiftCode("ABCDEFGHIXXX");
    value.setShortName("A.B.C.D");
    value.setAddress(sample77());
    value.setContactPerson(sample78());
    return value;
  }

  private static CurrencyData sample80() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("AED");
    value.setDisplayName("United Arab Emirates Dirham");
    value.setNumericCode(784);
    value.setSymbol("AED");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample81() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("JOD");
    value.setDisplayName("Jordanian Dinar");
    value.setNumericCode(400);
    value.setSymbol("JOD");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample82() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SYP");
    value.setDisplayName("Syrian Pound");
    value.setNumericCode(760);
    value.setSymbol("SYP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample83() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("HRK");
    value.setDisplayName("Kuna");
    value.setNumericCode(191);
    value.setSymbol("HRK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample84() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample85() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("PAB");
    value.setDisplayName("Panamanian Balboa");
    value.setNumericCode(590);
    value.setSymbol("PAB");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample86() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample87() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("VEF");
    value.setDisplayName("Venezuelan BolÃ­var");
    value.setNumericCode(937);
    value.setSymbol("VEF");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample88() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("TWD");
    value.setDisplayName("New Taiwan Dollar");
    value.setNumericCode(901);
    value.setSymbol("TWD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample89() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("DKK");
    value.setDisplayName("Danish Krone");
    value.setNumericCode(208);
    value.setSymbol("DKK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample90() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("USD");
    value.setDisplayName("US Dollar");
    value.setNumericCode(840);
    value.setSymbol("$");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample91() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("VND");
    value.setDisplayName("Vietnamese Dong");
    value.setNumericCode(704);
    value.setSymbol("VND");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample92() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("USD");
    value.setDisplayName("US Dollar");
    value.setNumericCode(840);
    value.setSymbol("$");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample93() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample94() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SEK");
    value.setDisplayName("Swedish Krona");
    value.setNumericCode(752);
    value.setSymbol("SEK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample95() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BOB");
    value.setDisplayName("Bolivian Boliviano");
    value.setNumericCode(68);
    value.setSymbol("BOB");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample96() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SGD");
    value.setDisplayName("Singapore Dollar");
    value.setNumericCode(702);
    value.setSymbol("SGD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample97() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BHD");
    value.setDisplayName("Bahraini Dinar");
    value.setNumericCode(48);
    value.setSymbol("BHD");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample98() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SAR");
    value.setDisplayName("Saudi Riyal");
    value.setNumericCode(682);
    value.setSymbol("SAR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample99() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("YER");
    value.setDisplayName("Yemeni Rial");
    value.setNumericCode(886);
    value.setSymbol("YER");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample100() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("INR");
    value.setDisplayName("Indian Rupee");
    value.setNumericCode(356);
    value.setSymbol("INR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample101() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample102() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample103() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BAM");
    value.setDisplayName("Bosnia-Herzegovina Convertible Mark");
    value.setNumericCode(977);
    value.setSymbol("BAM");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample104() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("UAH");
    value.setDisplayName("Ukrainian Hryvnia");
    value.setNumericCode(980);
    value.setSymbol("UAH");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample105() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CHF");
    value.setDisplayName("Swiss Franc");
    value.setNumericCode(756);
    value.setSymbol("CHF");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample106() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("ARS");
    value.setDisplayName("Argentine Peso");
    value.setNumericCode(32);
    value.setSymbol("ARS");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample107() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EGP");
    value.setDisplayName("Egyptian Pound");
    value.setNumericCode(818);
    value.setSymbol("EGP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample108() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("JPY");
    value.setDisplayName("Japanese Yen");
    value.setNumericCode(392);
    value.setSymbol("JPY");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample109() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SVC");
    value.setDisplayName("Salvadoran ColÃ³n");
    value.setNumericCode(222);
    value.setSymbol("SVC");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample110() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BRL");
    value.setDisplayName("Brazilian Real");
    value.setNumericCode(986);
    value.setSymbol("BRL");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample111() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("ISK");
    value.setDisplayName("Icelandic KrÃ³na");
    value.setNumericCode(352);
    value.setSymbol("ISK");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample112() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CZK");
    value.setDisplayName("Czech Republic Koruna");
    value.setNumericCode(203);
    value.setSymbol("CZK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample113() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("PLN");
    value.setDisplayName("Polish Zloty");
    value.setNumericCode(985);
    value.setSymbol("PLN");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample114() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample115() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CSD");
    value.setDisplayName("Serbian Dinar (2002-2006)");
    value.setNumericCode(891);
    value.setSymbol("CSD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample116() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("MYR");
    value.setDisplayName("Malaysian Ringgit");
    value.setNumericCode(458);
    value.setSymbol("MYR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample117() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample118() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("COP");
    value.setDisplayName("Colombian Peso");
    value.setNumericCode(170);
    value.setSymbol("COP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample119() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BGN");
    value.setDisplayName("Bulgarian Lev");
    value.setNumericCode(975);
    value.setSymbol("BGN");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample120() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BAM");
    value.setDisplayName("Bosnia-Herzegovina Convertible Mark");
    value.setNumericCode(977);
    value.setSymbol("BAM");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample121() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("PYG");
    value.setDisplayName("Paraguayan Guarani");
    value.setNumericCode(600);
    value.setSymbol("PYG");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample122() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("USD");
    value.setDisplayName("US Dollar");
    value.setNumericCode(840);
    value.setSymbol("$");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample123() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("USD");
    value.setDisplayName("US Dollar");
    value.setNumericCode(840);
    value.setSymbol("$");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample124() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SDG");
    value.setDisplayName("Sudanese Pound");
    value.setNumericCode(938);
    value.setSymbol("SDG");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample125() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("RON");
    value.setDisplayName("Romanian Leu");
    value.setNumericCode(946);
    value.setSymbol("RON");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample126() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("PHP");
    value.setDisplayName("Philippine Peso");
    value.setNumericCode(608);
    value.setSymbol("PHP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample127() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("TND");
    value.setDisplayName("Tunisian Dinar");
    value.setNumericCode(788);
    value.setSymbol("TND");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample128() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample129() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("GTQ");
    value.setDisplayName("Guatemalan Quetzal");
    value.setNumericCode(320);
    value.setSymbol("GTQ");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample130() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KRW");
    value.setDisplayName("South Korean Won");
    value.setNumericCode(410);
    value.setSymbol("KRW");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample131() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample132() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("MXN");
    value.setDisplayName("Mexican Peso");
    value.setNumericCode(484);
    value.setSymbol("MXN");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample133() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("RUB");
    value.setDisplayName("Russian Ruble");
    value.setNumericCode(643);
    value.setSymbol("RUB");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample134() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("HNL");
    value.setDisplayName("Honduran Lempira");
    value.setNumericCode(340);
    value.setSymbol("HNL");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample135() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("HKD");
    value.setDisplayName("Hong Kong Dollar");
    value.setNumericCode(344);
    value.setSymbol("HKD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample136() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("NOK");
    value.setDisplayName("Norwegian Krone");
    value.setNumericCode(578);
    value.setSymbol("NOK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample137() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("HUF");
    value.setDisplayName("Hungarian Forint");
    value.setNumericCode(348);
    value.setSymbol("HUF");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample138() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("THB");
    value.setDisplayName("Thai Baht");
    value.setNumericCode(764);
    value.setSymbol("THB");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample139() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("IQD");
    value.setDisplayName("Iraqi Dinar");
    value.setNumericCode(368);
    value.setSymbol("IQD");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample140() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CLP");
    value.setDisplayName("Chilean Peso");
    value.setNumericCode(152);
    value.setSymbol("CLP");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample141() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("MAD");
    value.setDisplayName("Moroccan Dirham");
    value.setNumericCode(504);
    value.setSymbol("MAD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample142() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample143() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("TRY");
    value.setDisplayName("Turkish Lira");
    value.setNumericCode(949);
    value.setSymbol("TRY");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample144() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample145() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("QAR");
    value.setDisplayName("Qatari Rial");
    value.setNumericCode(634);
    value.setSymbol("QAR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample146() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample147() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample148() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("OMR");
    value.setDisplayName("Omani Rial");
    value.setNumericCode(512);
    value.setSymbol("OMR");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample149() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("ALL");
    value.setDisplayName("Albanian Lek");
    value.setNumericCode(8);
    value.setSymbol("ALL");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample150() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("DOP");
    value.setDisplayName("Dominican Peso");
    value.setNumericCode(214);
    value.setSymbol("DOP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample151() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CUP");
    value.setDisplayName("Cuban Peso");
    value.setNumericCode(192);
    value.setSymbol("CUP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample152() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("NZD");
    value.setDisplayName("New Zealand Dollar");
    value.setNumericCode(554);
    value.setSymbol("NZD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample153() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("RSD");
    value.setDisplayName("Serbian Dinar");
    value.setNumericCode(941);
    value.setSymbol("RSD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample154() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CHF");
    value.setDisplayName("Swiss Franc");
    value.setNumericCode(756);
    value.setSymbol("CHF");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample155() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("UYU");
    value.setDisplayName("Uruguayan Peso");
    value.setNumericCode(858);
    value.setSymbol("UYU");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample156() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample157() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("ILS");
    value.setDisplayName("Israeli New Sheqel");
    value.setNumericCode(376);
    value.setSymbol("ILS");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample158() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("ZAR");
    value.setDisplayName("South African Rand");
    value.setNumericCode(710);
    value.setSymbol("ZAR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample159() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("THB");
    value.setDisplayName("Thai Baht");
    value.setNumericCode(764);
    value.setSymbol("THB");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample160() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample161() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample162() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("NOK");
    value.setDisplayName("Norwegian Krone");
    value.setNumericCode(578);
    value.setSymbol("NOK");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample163() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("AUD");
    value.setDisplayName("Australian Dollar");
    value.setNumericCode(36);
    value.setSymbol("AUD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample164() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample165() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CAD");
    value.setDisplayName("Canadian Dollar");
    value.setNumericCode(124);
    value.setSymbol("CAD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample166() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample167() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample168() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CRC");
    value.setDisplayName("Costa Rican ColÃ³n");
    value.setNumericCode(188);
    value.setSymbol("CRC");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample169() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("KWD");
    value.setDisplayName("Kuwaiti Dinar");
    value.setNumericCode(414);
    value.setSymbol("KWD");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample170() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("LYD");
    value.setDisplayName("Libyan Dinar");
    value.setNumericCode(434);
    value.setSymbol("LYD");
    value.setDefaultFractionDigits(3);
    return value;
  }

  private static CurrencyData sample171() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CHF");
    value.setDisplayName("Swiss Franc");
    value.setNumericCode(756);
    value.setSymbol("CHF");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample172() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample173() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("DZD");
    value.setDisplayName("Algerian Dinar");
    value.setNumericCode(12);
    value.setSymbol("DZD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample174() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample175() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample176() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample177() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample178() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("SGD");
    value.setDisplayName("Singapore Dollar");
    value.setNumericCode(702);
    value.setSymbol("SGD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample179() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CAD");
    value.setDisplayName("Canadian Dollar");
    value.setNumericCode(124);
    value.setSymbol("CAD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample180() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample181() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("CNY");
    value.setDisplayName("Chinese Yuan");
    value.setNumericCode(156);
    value.setSymbol("CNY");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample182() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("JPY");
    value.setDisplayName("Japanese Yen");
    value.setNumericCode(392);
    value.setSymbol("JPY");
    value.setDefaultFractionDigits(0);
    return value;
  }

  private static CurrencyData sample183() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample184() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("RSD");
    value.setDisplayName("Serbian Dinar");
    value.setNumericCode(941);
    value.setSymbol("RSD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample185() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("INR");
    value.setDisplayName("Indian Rupee");
    value.setNumericCode(356);
    value.setSymbol("INR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample186() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("LBP");
    value.setDisplayName("Lebanese Pound");
    value.setNumericCode(422);
    value.setSymbol("LBP");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample187() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("NIO");
    value.setDisplayName("Nicaraguan CÃ³rdoba");
    value.setNumericCode(558);
    value.setSymbol("NIO");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample188() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("MKD");
    value.setDisplayName("Macedonian Denar");
    value.setNumericCode(807);
    value.setSymbol("MKD");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample189() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("BYN");
    value.setDisplayName("Belarusian Ruble");
    value.setNumericCode(933);
    value.setSymbol("BYN");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample190() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("EUR");
    value.setDisplayName("Euro");
    value.setNumericCode(978);
    value.setSymbol("EUR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample191() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("PEN");
    value.setDisplayName("Peruvian Sol");
    value.setNumericCode(604);
    value.setSymbol("PEN");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample192() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("IDR");
    value.setDisplayName("Indonesian Rupiah");
    value.setNumericCode(360);
    value.setSymbol("IDR");
    value.setDefaultFractionDigits(2);
    return value;
  }

  private static CurrencyData sample193() {
    CurrencyData value = new CurrencyData();
    value.setCurrencyCode("GBP");
    value.setDisplayName("British Pound Sterling");
    value.setNumericCode(826);
    value.setSymbol("GBP");
    value.setDefaultFractionDigits(2);
    return value;
  }
}
