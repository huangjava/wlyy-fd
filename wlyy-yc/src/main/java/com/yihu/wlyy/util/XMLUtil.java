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
	 * ��xml�ַ���ת���� Document����
	 *
	 * @param xml
	 *            ��Ҫת����xml�ַ���
	 * @return ����Document����
	 * @throws Exception
	 *             ���ת����Document�����쳣�Ļ��׳��쳣��
	 */
	public static Document parseXml(String xml) throws Exception {
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("����� xml ���Ǳ�׼��xml�ַ����������ַ����Ƿ�Ϸ���");
		}
	}
	/**
	 * ת����xml�ַ���
	 *
	 * @param xmlDoc
	 *            ��Ҫ������xml����
	 * @throws Exception
	 */
	public static String toXML_UTF_8(org.dom4j.Document xmlDoc) throws Exception {
		return toXML(xmlDoc, "UTF-8", true);
	}
	/**
	 * ת����xml�ַ���
	 *
	 * @param xmlDoc
	 *            ��Ҫ������xml����
	 * @throws Exception
	 */
	public static String toXML_GBK(org.dom4j.Document xmlDoc) throws Exception {
		return toXML(xmlDoc, "GBK", true);
	}
	/**
	 * ת����xml�ַ���
	 *
	 * @param xmlDoc
	 *            ��Ҫ������xml����
	 * @param encoding
	 *            �����ʽ��UTF-8��GBK
	 * @param iscom
	 *            �Ƿ�Ϊ�����͸�ʽ
	 * @return ������ɺ��xml�ַ���
	 * @throws Exception
	 */
	public static String toXML(org.dom4j.Document xmlDoc, String encoding,
							   boolean iscom) throws Exception {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		OutputFormat format = null;
		if (iscom) {
			format = OutputFormat.createCompactFormat();// �����͸�ʽ
		} else {
			format = OutputFormat.createPrettyPrint();// �����͸�ʽ
		}
		format.setEncoding(encoding);// ���ñ���
		format.setTrimText(false);// ����text���Ƿ�Ҫɾ�����ж���Ŀո�
		XMLWriter xw;
		try {
			xw = new XMLWriter(byteRep, format);
			xw.write(xmlDoc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("����ı����ʽ�����봫����ȷ�ı��롣");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("�ĵ�ת����xml�ַ���ʱ����"+xmlDoc.asXML());
		}
		return byteRep.toString();
	}
	/**
	 * �Խڵ�Element ��ӽڵ㡣
	 * @param e ��Ҫ��ӵĽڵ�
	 * @param name ��ӵĽڵ������
	 * @param value ��ӵ�����
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
	 * �ж϶����Ƿ�Ϊ�գ�(null,"", "null")
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
	 * �ж϶����Ƿ�ǿգ�(null,"", "null")
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}
	/**
	 * �ж϶����Ƿ�Ϊ�գ�(null,"", "null")
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
	 * ���CDATA ���ͽڵ�
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
	 * ���CDATA ���ͽڵ�
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

	/**��ȡ�ڵ��е�����
	 * @throws Exception */
	public static  int  getInt(Element e,String name,boolean isMust) throws Exception
	{
		Element  current=e.element(name);


		if(current==null||current.getText()==null || "".equals(current.getText().trim()) || current.getText().length()<=0)
		{
			if(isMust){
				throw new Exception("�� $"+ e.asXML() +"$�л�ȡ�ڵ㣺"+name +" ��ֵΪ�ա�");
			}
			return 0;
		}
		Integer i = 0;
		try{
			i =Integer.parseInt(current.getTextTrim());
		}catch (NumberFormatException e1) {
			i = 0;
			if(isMust){
				throw new Exception("�� $"+ e.asXML() +"$�л�ȡ�ڵ㣺"+name +" ��ֵ�������Ρ�");
			}
		}
		return i;
	}
	/**��ȡ�ڵ��е��ַ���
	 * @throws Exception */
	public static  String  getString(Element e,String name,boolean isMust) throws Exception{
		return getString(e, name, isMust,null);
	}
	/**
	 * ��ȡ�ڵ��е��ַ���
	 * @param e
	 * @param name
	 * @param isMust	�Ƿ���� true ����  false�Ǳ���
	 * @param defVal	Ĭ��ֵ
	 * @return
	 * @throws Exception
	 */
	public static  String  getString(Element e,String name,boolean isMust, String defVal) throws Exception{
		Element  current=e.element(name);
		if(current==null||current.getText()==null || StringUtils.isEmpty(current.getText().trim()))
		{
			if(isMust){
				throw new Exception("�� $"+ e.asXML() +"$�л�ȡ�ڵ㣺"+name +" ��ֵΪ�ա�");
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
//		addElement(root, "A", "�����·�");
//		addElement(root, "A", 2);
//		System.out.println("����ӽڵ㣺\r\n"+toXML(doc, "GBK", true));
//		addCDATAElement(root, "B", "��������");
//		System.out.println("��� CDATA �ӽڵ㣺\r\n"+toXML(doc, "UTF-8", true));

		String xml = "<Resp><TransactionCode></TransactionCode><RespCode>10000</RespCode><RespMessage>ҽ����ѯ��Ϣ�ɹ�</RespMessage><Data><DeptCode>286</DeptCode><DoctorName>�¾���</DoctorName><Spec>�ڹǿƷ����нϸߵ����裬�������ҿƿ�չ�˹��š�ϥ�ؽ��û��������ó��˹��š�ϥ�ؽ��û����ؽ���ƣ�����ҽ������ƹ��ۡ��������������Ի��Ρ���������֯���ˣ�����֫�ǹ��۱պϸ�λ����һ�ġ�</Spec><DoctorTitle>����ҽʦ</DoctorTitle><DoctorCode>164</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>����</DoctorName><Spec>����ؽڼ��������˹ǿơ�������ơ�</Spec><DoctorTitle>����ҽʦ</DoctorTitle><DoctorCode>266</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>����</DoctorName><Spec>�Ƴ�����AO����������ҽ������Ƹ��ֹ��ۣ����ֹؽڼ����������ǲ������ƣ�С���ǿƺ͹ؽھ�΢���������ڴ��˽��Ρ��ؽ���ơ���������н������ʶ��������һЩ�ɼ���</Spec><DoctorTitle>����ҽʦ</DoctorTitle><DoctorCode>169</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>�ſ�ΰ</DoctorName><Spec>�ó��˹��ؽ��û����ؽھ��������ǿ�΢��������</Spec><DoctorTitle>����ҽʦ</DoctorTitle><DoctorCode>174</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>ףǬ��</DoctorName><Spec>�ó��ַ����۸�λ���ǿ�΢��������</Spec><DoctorTitle>����ҽʦ</DoctorTitle><DoctorCode>168</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>������</DoctorName><Spec>����ؽڼ��������˹ǿơ�������ơ�</Spec><DoctorTitle>������ҽʦ</DoctorTitle><DoctorCode>176</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>���</DoctorName><Spec>���˿�������������ʹ�����Թؽ��׼���������֢������ҽ���ơ�</Spec><DoctorTitle>������ҽʦ</DoctorTitle><DoctorCode>172</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>������</DoctorName><Spec>�ڹɹ�ͷ�����������ס��ǽ�ˡ������Ը�Ⱦ�Ȳ�������ҽ��������ľ���ɫ��</Spec><DoctorTitle>������ҽʦ</DoctorTitle><DoctorCode>171</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>��</DoctorName><Spec>�ó������Ƹ��ִ��ˡ���׵�����ɹ�ͷȱѪ��������������֢���ǹؽ��׵ȹǿƼ����Լ�С���ǿ�����ļ�����</Spec><DoctorTitle>������ҽʦ</DoctorTitle><DoctorCode>173</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>�ܽ���</DoctorName><Spec>�ó������ؽڼ�������׵ǰ��·��������׵����ͻ��֢����׵����խ�Լ������������������ơ�</Spec><DoctorTitle>������ҽʦ</DoctorTitle><DoctorCode>4545</DoctorCode><DeptName>����ʡ����ҽԺ</DeptName></Data></Resp>";
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
