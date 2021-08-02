package enums;

public enum MrzDocumentCode {
	Passport,   //A passport, P or IP ... maybe Travel Document that is very similar to the passport.
	TypeI,      //General I type (besides IP).
	TypeA,      //General A type (besides AC).
	CrewMember, //Crew member (AC).
	TypeC,      //General type C.
	TypeV,      //Type V (Visa).
	Migrant;     //Migrant

	public static MrzDocumentCode fromMrz(String mrz) throws Exception {
		final String code = mrz.substring(0, 2);

		// 2-letter checks
		switch(code){
			case "IV":
				throw new Exception();
			case "AC": return CrewMember;
			case "ME": return Migrant;
			case "TD": return Migrant; // travel document
			case "IP": return Passport;
		}

		// 1-letter checks
		switch(code.charAt(0)){
			case 'T':   // usually Travel Document
			case 'P': return Passport;
			case 'A': return TypeA;
			case 'C': return TypeC;
			case 'V': return TypeV;
			case 'I': return TypeI; // identity card or residence permit
			case 'R': return Migrant;  // swedish '51 Convention Travel Document
		}
		throw new Exception();
	}
}
