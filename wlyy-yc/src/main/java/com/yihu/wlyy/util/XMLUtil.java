package com.yihu.wlyy.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class XMLUtil {

    public static Logger logger = Logger.getLogger(XMLUtil.class);

    /**
     * 把xml字符串转换成 Document对象。
     *
     * @param xml 需要转换的xml字符串
     * @return 返回Document对象
     * @throws Exception 如果转换成Document对象异常的话抛出异常。
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
     * @param xmlDoc 需要解析的xml对象
     * @throws Exception
     */
    public static String toXML_UTF_8(Document xmlDoc) throws Exception {
        return toXML(xmlDoc, "UTF-8", true);
    }

    /**
     * 转换成xml字符串
     *
     * @param xmlDoc 需要解析的xml对象
     * @throws Exception
     */
    public static String toXML_GBK(Document xmlDoc) throws Exception {
        return toXML(xmlDoc, "GBK", true);
    }

    /**
     * 转换成xml字符串
     *
     * @param xmlDoc   需要解析的xml对象
     * @param encoding 编码格式：UTF-8、GBK
     * @param iscom    是否为紧凑型格式
     * @return 修正完成后的xml字符串
     * @throws Exception
     */
    public static String toXML(Document xmlDoc, String encoding,
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
            throw new Exception("文档转换成xml字符串时出错。" + xmlDoc.asXML());
        }
        return byteRep.toString();
    }

    /**
     * 对节点Element 添加节点。
     *
     * @param e     需要添加的节点
     * @param name  添加的节点的名称
     * @param value 添加的内容
     *              <br/>
     *              Demo:
     *              < Root > aaa < /Root >
     *              <br/>
     *              call-->  addChildElement(root, "A", "a");
     *              <br/>
     *              result--> < Root >< A >a< /A >< /Root >
     */
    public static void addElement(Element e, String name, Object value) {
        if (isBlank(value)) {
            e.addElement(name).addText("");
        } else {
            e.addElement(name).addText(value.toString());
        }
    }

    /**
     * 判断对象是否为空！(null,"", "null")
     *
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
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断对象是否为空！(null,"", "null")
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isBlank((String) obj);
        } else {
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
     *
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
     *
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

    /**
     * 获取节点中的整数
     *
     * @throws Exception
     */
    public static int getInt(Element e, String name, boolean isMust) throws Exception {
        Element current = e.element(name);


        if (current == null || current.getText() == null || "".equals(current.getText().trim()) || current.getText().length() <= 0) {
            if (isMust) {
                throw new Exception("在 $" + e.asXML() + "$中获取节点：" + name + " 的值为空。");
            }
            return 0;
        }
        Integer i = 0;
        try {
            i = Integer.parseInt(current.getTextTrim());
        } catch (NumberFormatException e1) {
            i = 0;
            if (isMust) {
                throw new Exception("在 $" + e.asXML() + "$中获取节点：" + name + " 的值不是整形。");
            }
        }
        return i;
    }

    /**
     * 获取节点中的字符串
     *
     * @throws Exception
     */
    public static String getString(Element e, String name, boolean isMust) throws Exception {
        return getString(e, name, isMust, null);
    }

    /**
     * 获取节点中的字符串
     *
     * @param e
     * @param name
     * @param isMust 是否必填 true 必填  false非必填
     * @param defVal 默认值
     * @return
     * @throws Exception
     */
    public static String getString(Element e, String name, boolean isMust, String defVal) throws Exception {
        Element current = e.element(name);
        if (current == null || current.getText() == null || StringUtils.isEmpty(current.getText().trim())) {
            if (isMust) {
                throw new Exception("在 $" + e.asXML() + "$中获取节点：" + name + " 的值为空。");
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

    public static String xml2JSON(String xml) {
        if (StringUtils.isEmpty(xml)) {
            return "";
        } else {
            return new XMLSerializer().read(xml).toString();
        }
    }

    public static String json2XML(String json) {
        if (StringUtils.isEmpty(json)) {
            return "";
        } else {
            XMLSerializer xmlSerializer = new XMLSerializer();
            JSONObject jobj = JSONObject.fromObject(json);
            String xmlStr = xmlSerializer.write(jobj);
            String xml = xmlStr.replace(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").replace(
                    "<o>", "").replace("</o>", "");
            return xml;
        }
    }

    public static String json2XML(String jsonStr, String rootName) {
        JSONObject json = JSONObject.fromObject(jsonStr);
        StringBuffer reqXml = new StringBuffer();
        reqXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + rootName + ">");
        reqXml.append(GetXml(json));
        reqXml.append("</" + rootName + ">");
        String rt = reqXml.toString();
        return rt;
    }

    public static String jsonToXml(String jsonStr) {
        JSONObject json = JSONObject.fromObject(jsonStr);
        StringBuffer reqXml = new StringBuffer();
        reqXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        reqXml.append(GetXml(json));
        String rt = reqXml.toString();
        return rt;
    }

    private static String GetXml(JSONObject json) {
        StringBuffer sb = new StringBuffer();
        if (json != null && !json.isNullObject()) {
            Iterator<?> iter = json.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (json.get(key) instanceof JSONObject) {
                    sb.append("<").append(key).append(">");
                    sb.append(GetXml(json.getJSONObject(key)));
                    sb.append("</").append(key).append(">");
                } else if (json.get(key) instanceof JSONArray) {
                    JSONArray array = json.getJSONArray(key);
                    if (array != null && array.size() > 0) {
                        for (int i = 0; i < array.size(); i++) {
                            sb.append("<").append(key).append(">");
                            sb.append(GetXml(array.getJSONObject(i)));
                            sb.append("</").append(key).append(">");
                        }
                    }
                } else {
                    sb.append("<").append(key).append(">").append(json.getString(key)).append("</").append(key).append(">");
                }
            }
        }
        return sb.toString();
    }

    //	public static void main(String[] args) throws Exception {
//
////		Document doc = parseXml("<Root></Root>");
////	``	Element root = doc.getRootElement();
////		addElement(root, "A", "阿萨德飞");
////		addElement(root, "A", 2);
////		System.out.println("添加子节点：\r\n"+toXML(doc, "GBK", true));
////		addCDATAElement(root, "B", "所发生的");
////		System.out.println("添加 CDATA 子节点：\r\n"+toXML(doc, "UTF-8", true));
//
//		String xml = "<Resp><TransactionCode></TransactionCode><RespCode>10000</RespCode><RespMessage>医生查询信息成功</RespMessage><Data><DeptCode>286</DeptCode><DoctorName>陈久毅</DoctorName><Spec>在骨科方面有较高的造诣，率先于我科开展人工髋、膝关节置换手术，擅长人工髋、膝关节置换，关节外科，中西医结合治疗骨折、骨肿瘤、先天性畸形、各种软组织损伤，对四肢骨骨折闭合复位独树一帜。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>164</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>田民</DoctorName><Spec>骨与关节疾病、创伤骨科、脊柱外科。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>266</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>王松</DoctorName><Spec>善长运用AO技术、中西医结合治疗各种骨折；各种关节疾患、脊柱骨病的治疗；小儿骨科和关节镜微创技术，在创伤矫形、关节外科、脊柱外科有较深的认识并作出了一些成绩。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>169</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>张开伟</DoctorName><Spec>擅长人工关节置换、关节镜及其它骨科微创手术。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>174</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>祝乾清</DoctorName><Spec>擅长手法骨折复位、骨科微创手术。</Spec><DoctorTitle>主任医师</DoctorTitle><DoctorCode>168</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>方明智</DoctorName><Spec>骨与关节疾病、创伤骨科、脊柱外科。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>176</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>李贵华</DoctorName><Spec>创伤康复、颈肩腰腿痛、骨性关节炎及骨质疏松症的中西医治疗。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>172</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>李玉雄</DoctorName><Spec>在股骨头坏死、骨髓炎、骨结核、外伤性感染等病的中西医结合治疗颇具特色。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>171</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>沈俊</DoctorName><Spec>擅长于治疗各种创伤、颈椎病、股骨头缺血坏死、骨质疏松症、骨关节炎等骨科疾病以及小儿骨科领域的疾患。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>173</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data><Data><DeptCode>286</DeptCode><DoctorName>周建鸿</DoctorName><Spec>擅长脊柱关节疾病、颈椎前后路手术、腰椎间盘突出症、腰椎管狭窄以及脊柱肿瘤的手术治疗。</Spec><DoctorTitle>副主任医师</DoctorTitle><DoctorCode>4545</DoctorCode><DeptName>贵州省骨伤医院</DeptName></Data></Resp>";
//		System.out.println(formatXML(parseXml(xml)));
//	}
    public static String formatXML(Document doc) throws Exception {
        StringWriter out = null;
        try {
            OutputFormat formate = OutputFormat.createPrettyPrint();
            out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, formate);
            writer.write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        return out.toString();
    }


    // ---------- map转XML--------------
    public static String map2xml(Map<String, String> dataMap) {
        synchronized (XMLUtil.class) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            strBuilder.append("<QUERY_FORM>");
            Set<String> objSet = dataMap.keySet();
            for (Object key : objSet) {
                if (key == null) {
                    continue;
                }
                strBuilder.append("<").append(key.toString()).append(">");
                Object value = dataMap.get(key);
                strBuilder.append(coverter(value));
                strBuilder.append("</").append(key.toString()).append(">");
            }
            strBuilder.append("</QUERY_FORM>");
            return strBuilder.toString();
        }
    }

    public static String coverter(Object[] objects) {
        StringBuilder strBuilder = new StringBuilder();
        for (Object obj : objects) {
            strBuilder.append("<item className=").append(obj.getClass().getName()).append(">\n");
            strBuilder.append(coverter(obj));
            strBuilder.append("</item>\n");
        }
        return strBuilder.toString();
    }

    public static String coverter(Collection<?> objects) {
        StringBuilder strBuilder = new StringBuilder();
        for (Object obj : objects) {
            strBuilder.append("<item className=").append(obj.getClass().getName()).append(">\n");
            strBuilder.append(coverter(obj));
            strBuilder.append("</item>\n");
        }
        return strBuilder.toString();
    }

    public static String coverter(Object object) {
        if (object instanceof Object[]) {
            return coverter((Object[]) object);
        }
        if (object instanceof Collection) {
            return coverter((Collection<?>) object);
        }
        StringBuilder strBuilder = new StringBuilder();
        if (isObject(object)) {
            Class<? extends Object> clz = object.getClass();
            Field[] fields = clz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field == null) {
                    continue;
                }
                String fieldName = field.getName();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalArgumentException e) {
                    continue;
                } catch (IllegalAccessException e) {
                    continue;
                }
                strBuilder.append("<").append(fieldName)
                        .append(" className=\"").append(
                        value.getClass().getName()).append("\">");
                if (isObject(value)) {
                    strBuilder.append(coverter(value));
                } else if (value == null) {
                    strBuilder.append("null");
                } else {
                    strBuilder.append(value.toString() + "");
                }
                strBuilder.append("</").append(fieldName).append(">");
            }
        } else if (object == null) {
            strBuilder.append("null");
        } else {
            strBuilder.append(object.toString());
        }
        return strBuilder.toString();
    }

    private static boolean isObject(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return false;
        }
        if (obj instanceof Integer) {
            return false;
        }
        if (obj instanceof Double) {
            return false;
        }
        if (obj instanceof Float) {
            return false;
        }
        if (obj instanceof Byte) {
            return false;
        }
        if (obj instanceof Long) {
            return false;
        }
        if (obj instanceof Character) {
            return false;
        }
        if (obj instanceof Short) {
            return false;
        }
        if (obj instanceof Boolean) {
            return false;
        }
        return true;
    }

    // --------------------xml转map---------------
    public static Map xml2map(String xmlString) throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream in_nocode = new ByteArrayInputStream(xmlString.getBytes());
        Document doc = reader.read(in_nocode);

        Element rootElement = doc.getRootElement();
        Map<String, Object> map = new HashMap<String, Object>();
        ele2map(map, rootElement);
        System.out.println(map);
        // 到此xml2map完成，下面的代码是将map转成了json用来观察我们的xml2map转换的是否ok
        String string = JSONObject.fromObject(map).toString();
        System.out.println(string);
        return map;
    }

    private static void ele2map(Map map, Element ele) {
        System.out.println(ele);
        // 获得当前节点的子节点
        List<Element> elements = ele.elements();
        if (elements.size() == 0) {
            // 没有子节点说明当前节点是叶子节点，直接取值即可
            map.put(ele.getName(), ele.getText());
        } else if (elements.size() == 1) {
            // 只有一个子节点说明不用考虑list的情况，直接继续递归即可
            Map<String, Object> tempMap = new HashMap<String, Object>();
            ele2map(tempMap, elements.get(0));
            map.put(ele.getName(), tempMap);
        } else {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (Element element : elements) {
                tempMap.put(element.getName(), null);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = elements.get(0).getNamespace();
                List<Element> elements2 = ele.elements(new QName(string, namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Map> list = new ArrayList<Map>();
                    for (Element element : elements2) {
                        Map<String, Object> tempMap1 = new HashMap<String, Object>();
                        ele2map(tempMap1, element);
                        list.add(tempMap1);
                    }
                    map.put(string, list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    Map<String, Object> tempMap1 = new HashMap<String, Object>();
                    ele2map(tempMap1, elements2.get(0));
                    map.put(string, tempMap1);
                }
            }
        }
    }



    public static Map xmltoMap(String xml) {
        if (!StringUtil.isEmpty(xml)) {
            try {
                Map map = new HashMap();
                Document document = DocumentHelper.parseText(xml);
                Element nodeElement = document.getRootElement();
                List node = nodeElement.elements();
                for (Iterator it = node.iterator(); it.hasNext(); ) {
                    Element elm = (Element) it.next();
                    map.put(elm.getName(), elm.getText());
                    elm = null;
                }
                node = null;
                nodeElement = null;
                document = null;
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List xmltoList(String xml) {
        if (!StringUtil.isEmpty(xml)) {
            try {
                List<Map> list = new ArrayList<Map>();
                Document document = DocumentHelper.parseText(xml);
                Element nodesElement = document.getRootElement();
                List nodes = nodesElement.elements();
                for (Iterator its = nodes.iterator(); its.hasNext(); ) {
                    Element nodeElement = (Element) its.next();
                    Map map = xmltoMap(nodeElement.asXML());
                    list.add(map);
                    map = null;
                }
                nodes = null;
                nodesElement = null;
                document = null;
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* ============================== 以下暂时添加，后面可删除 ========================================= */
//    public static String maptoXml(Map map) {
//        Document document = DocumentHelper.createDocument();
//        Element nodeElement = document.addElement("node");
//        for (Object obj : map.keySet()) {
//            Element keyElement = nodeElement.addElement("key");
//            keyElement.addAttribute("label", String.valueOf(obj));
//            keyElement.setText(String.valueOf(map.get(obj)));
//        }
//        return doc2String(document);
//    }
//
//    public static String listtoXml(List list) throws Exception {
//        Document document = DocumentHelper.createDocument();
//        Element nodesElement = document.addElement("nodes");
//        int i = 0;
//        for (Object o : list) {
//            Element nodeElement = nodesElement.addElement("node");
//            if (o instanceof Map) {
//                for (Object obj : ((Map) o).keySet()) {
//                    Element keyElement = nodeElement.addElement("key");
//                    keyElement.addAttribute("label", String.valueOf(obj));
//                    keyElement.setText(String.valueOf(((Map) o).get(obj)));
//                }
//            } else {
//                Element keyElement = nodeElement.addElement("key");
//                keyElement.addAttribute("label", String.valueOf(i));
//                keyElement.setText(String.valueOf(o));
//            }
//            i++;
//        }
//        return doc2String(document);
//    }
//
//    public static String doc2String(Document document) {
//        String s = "";
//        try {
//            // 使用输出流来进行转化
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            // 使用UTF-8编码
//            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
//            XMLWriter writer = new XMLWriter(out, format);
//            writer.write(document);
//            s = out.toString("UTF-8");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return s;
//    }

}
