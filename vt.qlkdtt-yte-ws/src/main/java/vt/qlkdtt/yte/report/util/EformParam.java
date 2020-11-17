package vn.com.fis.report;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: FPT - IS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EformParam implements java.io.Serializable
{
	private String key;
	private String value;

	public EformParam(String key,String value)
	{
		this.key = key;
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
