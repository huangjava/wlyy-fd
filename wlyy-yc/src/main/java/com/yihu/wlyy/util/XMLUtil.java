package com.yihu.wlyy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class XMLUtil {

	public static Logger logger  = Logger.getLogger(XMLUtil.class);

	/**
	 * 把xml字符串转换成 Document对象。
	 *
	 * @param xml
	 *            需要转换的xml字符串
	 * @return 返回Document对象
	 * @throws Exception
	 *             如果转换成Document对象异常的话抛出异常。
	 */
	public static Document parseXml(String xml) throws Exception {
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("传入的 xml 不是标准的xml字符串，请检查字符串是否合法。");
		}
	}
	/**
	 * 转换成xml字符串
	 *
	 * @param xmlDoc
	 *            需要解析的xml对象
	 * @throws Exception
	 */
	public static String toXML_UTF_8(org.dom4j.Document xmlDoc) throws Exception {
		return toXML(xmlDoc, "UTF-8", true);
	}
	/**
	 * 转换成xml字符串
	 *
	 * @param xmlDoc
	 *            需要解析的xml对象
	 * @throws Exception
	 */
	public static String toXML_GBK(org.dom4j.Document xmlDoc) throws Exception {
		return toXML(xmlDoc, "GBK", true);
	}
	/**
	 * 转换成xml字符串
	 *
	 * @param xmlDoc
	 *            需要解析的xml对象
	 * @param encoding
	 *            编码格式：UTF-8、GBK
	 * @param iscom
	 *            是否为紧凑型格式
	 * @return 修正完成后的xml字符串
	 * @throws Exception
	 */
	public static String toXML(org.dom4j.Document xmlDoc, String encoding,
							   boolean iscom) throws Exception {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		OutputFormat format = null;
		if (iscom) {
			format = OutputFormat.createCompactFormat();// 紧凑型格式
		} else {
			format = OutputFormat.createPrettyPrint();// 缩减型格式
		}
		format.setEncoding(encoding);// 设置编码
		format.setTrimText(false);// 设置text中是否要删除其中多余的空格
		XMLWriter xw;
		try {
			xw = new XMLWriter(byteRep, format);
			xw.write(xmlDoc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("传入的编码格式错误，请传入正确的编码。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("文档转换成xml字符串时出错。"+xmlDoc.asXML());
		}
		return byteRep.toString();
	}
	/**
	 * 对节点Element 添加节点。
	 * @param e 需要添加的节点
	 * @param name 添加的节点的名称
	 * @param value 添加的内容
	 * <br/>
	 * Demo:
	 * 	  < Root > aaa < /Root >
	 * <br/>
	 *  call-->  addChildElement(root, "A", "a");
	 *  <br/>
	 *  result--> < Root >< A >a< /A >< /Root >
	 */
	public static void addElement(Element e,String name,Object value){
		if(isBlank(value)){
			e.addElement(name).addText("");
		}else{
			e.addElement(name).addText(value.toString());
		}
	}
	/**
	 * 判断对象是否为空！(null,"", "null")
	 * @param value
	 * @return
	 */
	private static boolean isBlank(String value) {
		if (value == null || value.length() == 0) {
			return true;
		} else if (StringUtils.isEmpty(value)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断对象是否非空！(null,"", "null")
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}
	/**
	 * 判断对象是否为空！(null,"", "null")
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return isBlank((String) obj);
		}else {
			return isBlank(obj.toString());
		}
	}
	public static void addElement(Element e, String name, Integer value) {
		Element current = e.addElement(name);
		if (value != null) {
			current.setText(Integer.toString(value));
		}
	}

	/**
	 * 添加CDATA 类型节点
	 * @param e
	 * @param name
	 * @param value
	 */
	public static void addCDATAElement(Element e, String name, String value) {
		Element current = e.addElement(name);
		if (value != null) {
			current.addCDATA(value.trim());
		}
	}
	/**
	 * 添加CDATA 类型节点
	 * @param e
	 * @param name
	 * @param value
	 */
	public static void addCDATAElement(Element e, String name, Integer value) {
		Element current = e.addElement(name);
		if (value != null) {
			current.addCDATA(value.toString());
		}
	}

	/**获取节点中的整数
	 * @throws Exception */
	public static  int  getInt(Element e,String name,boolean isMust) throws Exception
	{
		Element  current=e.element(name);


		if(current==null||current.getText()==null || "".equals(current.getText().trim()) || current.getText().length()<=0)
		{
			if(isMust){
				throw new Exception("在 $"+ e.asXML() +"$中获取节点："+name +" 的值为空。");
			}
			return 0;
		}
		Integer i = 0;
		try{
			i =Integer.parseInt(current.getTextTrim());
		}catch (NumberFormatException e1) {
			i = 0;
			if(isMust){
				throw new Exception("在 $"+ e.asXML() +"$中获取节点："+name +" 的值不是整形。");
			}
		}
		return i;
	}
	/**获取节点中的字符串
	 * @throws Exception */
	public static  String  getString(Element e,String name,boolean isMust) throws Exception{
		return getString(e, name, isMust,null);
	}
	/**
	 * 获取节点中的字符串
	 * @param e
	 * @param name
	 * @param isMust	是否必填 true 必填  false非必填
	 * @param defVal	默认值
	 * @return
	 * @throws Exception
	 */
	public static  String  getString(Element e,String name,boolean isMust, String defVal) throws Exception{
		Element  current=e.element(name);
		if(current==null||current.getText()==null || StringUtils.isEmpty(current.getText().trim()))
		{
			if(isMust){
				throw new Exception("在 $"+ e.asXML() +"$中获取节点："+name +" 的值为空。");
			}
			return defVal;
		}
		return current.getTextTrim();
	}

	public static String ElementStringValue(String str) {
		if (str != null) {
			return str;
		} else {
			return "";
		}
	}

	public static Integer ElementIntegerValue(Integer str) {
		if (str != null) {
			return str;
		} else {
			return 0;
		}
	}

	public static String ElementValue(String str) {
		if (str != null && !str.equalsIgnoreCase("null")) {
			return str;
		} else {
			return "";
		}
	}

	public static String xml2JSON(String xml)
	{
		if(StringUtils.isEmpty(xml)){
			return "";
		}else{
			return new XMLSerializer().read(xml).toString();
		}
	}

	public static String json2XML(String json)
	{
		if(StringUtils.isEmpty(json)){
			return "";
		}else{
			net.sf.json.JSONObject jobj = net.sf.json.JSONObject.fromObject(json);
			String xml = new XMLSerializer().write(jobj).replace(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").replace(
					"<o>", "").replace("</o>", "");
			return xml;
		}
	}
	public static String json2XML(String jsonStr,String rootName)
	{
		JSONObject json = JSONObject.fromObject(jsonStr);
		StringBuffer reqXml =  new StringBuffer();
		reqXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><"+rootName+">");
		reqXml.append(GetXml(json));
		reqXml.append("</"+rootName+">");
		String rt = reqXml.toString();
		return rt;
	}
	public static String jsonToXml(String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		StringBuffer reqXml =  new StringBuffer();
		reqXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		reqXml.append(GetXml(json));
		String rt = reqXml.toString();
		return rt;
	}

	private static String GetXml(JSONObject json){
		StringBuffer sb = new StringBuffer();
		if(json!=null && !json.isNullObject()){
			Iterator<?> iter =  json.keys();
			while(iter.hasNext()){
				String key  = (String) iter.next();
				if(json.get(key) instanceof JSONObject){
					sb.append("<").append(key).append(">");
					sb.append(GetXml(json.getJSONObject(key)));
					sb.append("</").append(key).append(">");
				}else if(json.get(key) instanceof JSONArray){
					JSONArray array = json.getJSONArray(key);
					if(array!=null && array.size()>0){
						for(int i=0;i<array.size();i++){
							sb.append("<").append(key).append(">");
							sb.append(GetXml(array.getJSONObject(i)));
							sb.append("</").append(key).append(">");
						}
					}
				}else{
					sb.append("<").append(key).append(">").append(json.getString(key)).append("</").append(key).append(">");
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {

//		Document doc = parseXml("<Root></Root>");
//		Element root = doc.getRootElement();
//		addElement(root, "A", "阿萨德飞");
//		addElement(root, "A", 2);
//		System.out.println("添加子节点：\r\n"+toXML(doc, "GBK", true));
//		addCDATAElement(root, "B", "所发生的");
//		System.out.println("添加 CDATA 子节点：\r\n"+toXML(doc, "UTF-8", true));

		String xml = "<Resp><TransactionCode></TransactionCode><RespCode>10000</RespCode><RespMessage>医生查询信息成功</RespMessage><Data><DeptCode>286</DeptCode><DoctorName>陈久毅</DoctorName><Spec>在骨科方面有较高的造诣，率先于我科开展人工髋、膝关节置换手术，擅长人工髋、膝关节置换，关节外科，中西医结合治疗骨折、骨肿瘤、先天性畸形、各种软组织损伤，对四肢骨骨折闭合复位独树一帜。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>164</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>田民</DoctorName><Spec>骨与关节疾病、创伤骨科、脊柱外科。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>266</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>王松</DoctorName><Spec>善长运用AO技术、中西医结合治疗各种骨折；各种关节疾患、脊柱骨病的治疗；小儿骨科和关节镜微创技术，在创伤矫形、关节外科、脊柱外科有较深的认识并作出了一些成绩。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>169</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>张开伟</DoctorName><Spec>擅长人工关节置换、关节镜及其它骨科微创手术。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>174</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>祝乾清</DoctorName><Spec>擅长手法骨折复位、骨科微创手术。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>168</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>方明智</DoctorName><Spec>骨与关节疾病、创伤骨科、脊柱外科。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>176</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>李贵华</DoctorName><Spec>创伤康复、颈肩腰腿痛、骨性关节炎及骨质疏松症的中西医治疗。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>172</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>李玉雄</DoctorName><Spec>在股骨头坏死、骨髓炎、骨结核、外伤性感染等病的中西医结合治疗颇具特色。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>171</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>沈俊</DoctorName><Spec>擅长于治疗各种创伤、颈椎病、股骨头缺血坏死、骨质疏松症、骨关节炎等骨科疾病以及小儿骨科领域的疾患。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>173</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>周建鸿</DoctorName><Spec>擅长脊柱关节疾病、颈椎前后路手术、腰椎间盘突出症、腰椎管狭窄以及脊柱肿瘤的手术治疗。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>4545</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data></Resp>";
		System.out.println(formatXML(parseXml(xml)));
	}
	public static String formatXML(Document doc) throws Exception {
		StringWriter out=null;
		try{
			OutputFormat formate=OutputFormat.createPrettyPrint();
			out=new StringWriter();
			XMLWriter writer=new XMLWriter(out,formate);
			writer.write(doc);
		} catch (IOException e){
			e.printStackTrace();
		} finally{
			out.close();
		}
		return out.toString();
	}
}
