package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrzDate implements Serializable, Comparable<MrzDate> {
	private static final long serialVersionUID = 1L;

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour = 0;
	private Integer minute = 0;
	private Integer second = 0;
	private String mrzData;
	private Boolean isValid;

	public MrzDate(Integer year, Integer month, Integer day, String mrzData){
		this.year = year;
		this.month = month;
		this.day = day;
		this.mrzData = mrzData;
		this.isValid = isValid();
	}

	public int compareTo(MrzDate mrzDate) {
		return 0;
	}

	@Override
	public String toString(){
		return String.format("%02d/%02d/%02d", year, month, day);
	}

	public String toUTC(){
		Integer prefixYear = 20;
		if (year>50){ //----------- this is work till 2050
			prefixYear = 19;
		}
		return String.format("%02d%02d/%02d/%02dT%02d:%02d:%02dZ", prefixYear ,year, month, day, hour, minute, second);
	}

	private boolean isValid() {
		if (year < 0 || year > 99) {
			return false;
		}
		if (month < 1 || month > 12) {
			return false;
		}
		return day >= 1 && day <= 31;
	}
}
