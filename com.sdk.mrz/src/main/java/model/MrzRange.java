package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MrzRange implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer startIndex;
	private Integer endIndex;
	private Integer index;
	public Integer getLenght(){return endIndex - startIndex;}
}
