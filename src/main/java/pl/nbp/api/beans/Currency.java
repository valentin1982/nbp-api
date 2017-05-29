package pl.nbp.api.beans;

public class Currency {

	private String code;
	private String name;

	public Currency() {
		
	}

	/**
	 *
	 * @param code
	 * @param name
	 */
	public Currency(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[code = " + code + ", name = " + name + "]";
	}
}
