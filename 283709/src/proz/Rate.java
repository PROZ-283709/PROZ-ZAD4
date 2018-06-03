package proz;

import javax.xml.bind.annotation.XmlElement;

public class Rate
{
	@XmlElement(name = "Mid")
	private String Mid;
	@XmlElement(name = "Ask")
	private String Ask;
	
	public String getMid() 
	{
		return Mid;
	}
	
	public void setMid(String mid)
	{
		Mid = mid;
	}
	
	public String getAsk() 
	{
		return Ask;
	}
	
	public void setAsk(String ask) 
	{
		Ask = ask;
	}
}