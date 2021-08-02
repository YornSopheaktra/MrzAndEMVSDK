package enums;

public enum MrzSex {

	Male('M'),
	Female('F'),
	Unspecified('X');

	private final char sex;

	MrzSex(char sex) {
		this.sex = sex;
	}

	public char getSex() {
		return sex;
	}

	public static MrzSex fromMrz(char sex) {
		switch (sex) {
			case 'M':
			case 'm':
				return Male;
			case 'F':
			case 'f':
				return Female;
			case '<':
			case 'X':
			default:
				return Unspecified;
		}
	}
}
