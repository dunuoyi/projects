package com.dhcc.jcpt.utils.xml;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.dhcc.jcpt.exceptions.XmlPatternException;

/**
 * 解析xml文件（包括特殊字符，但是不支持节点之间有空格，内容可以为空格）
 * @author dujl
 */
public class XmlUtil {
	
	
	public static void main(String[] args) throws XmlPatternException {
			
		String aa = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+
			"<note>"+
			"<to>George</to>"+
			"<from>John</from>"+
			"<heading><aa>子节点</aa></heading>"+
			"<body>Don't forget the meeting!</body>"+
			"</note>";
		
		System.out.println(encodeXml(aa));

		
	}
	
	
	//把xml文件的值进行编码
	public static String encodeXml(String xml) throws XmlPatternException {
		if(xml!=null&&xml.contains(">")){
			//截取xml头部，去掉空格，转为小写
			String headerStr = xml.substring(1,xml.indexOf(">")).replaceAll(" ", "").toLowerCase();
			if(isOrNotXmlHeader(headerStr)){
				String contextStr = xml.substring(xml.indexOf(">")+1,xml.length());//截取内容
				return encodeNode(contextStr);//对内容进行解析
			}
		}
		throw new XmlPatternException("数据格式错误");
	}

	//支持多久xml文件，但内容不运行包含特殊字符
	private static String encodeNode(String context) throws XmlPatternException{
		if(isOrNotXml(context)){
			String field = getXmlField(context);//获取名称
			String value = getXmlValue(context);//获取值
			List<String> nodes = new ArrayList<String>();
			try {
				if(value.contains(">")&&initNodeList(nodes,value)){//判断值中是否有xml的子标签，如果有子标签，添加到nodes
					StringBuffer notesStr = new StringBuffer();
					for(String node:nodes){//对子标签遍历
						if(node.contains(">")){
							notesStr.append(encodeNode(node));//对子标签进行同样的操作
						}
					}
					return "<"+field+">"+notesStr.toString()+"</"+field+">";
				}else{//如果值没有子标签，直接进行编码
					return "<"+field+">"+URLEncoder.encode(value,"utf-8")+"</"+field+">";
				}
			} catch (UnsupportedEncodingException e) {
				throw new XmlPatternException("数据格式错误,对字符编码出现错误");
			}
		}
		return null;
	}
	
	/**
	 * 递归判断是否符合同级兄弟标签,<xxx></xxx><xxx></xxx>...是否成立，
	 * 并把每个兄弟标签，存放到node中
	 * @param nodes
	 * @param value
	 * @return
	 * @throws XmlPatternException
	 */
	private static boolean initNodeList(List<String> nodes,String contextXml) throws XmlPatternException {
		int begin = contextXml.indexOf(">");
		String endStr = "</"+contextXml.substring(1,begin)+">";//根据开始标签，拼的第一个<xxx></xxx>的</xxx>标签
		if(contextXml.indexOf(endStr)!=-1){//判断是否符合<xxx></xxx>
			String node = contextXml.substring(0,contextXml.indexOf(endStr))+endStr;//获取一个同级标签
			nodes.add(node);
			String newValue = contextXml.substring(contextXml.indexOf(endStr)+begin+2,contextXml.length());//获取同级剩余的部分
			if(newValue.contains(">")){//对剩余的不服你是否有xml兄弟标签
				return initNodeList(nodes,newValue);
			}
			return true;
		}else{//不包含子标签
			return false;
		}
	}
	
	/**
	 * 获取xml中的field值
	 * @param xmlContext
	 * @return
	 */
	private static String getXmlField(String xmlContext){
		return  xmlContext.substring(1,xmlContext.indexOf(">"));
	}
	
	/**
	 * 获取xml中的值
	 * @param xmlContext
	 * @return
	 */
	private static String getXmlValue(String xmlContext){
		int length = xmlContext.indexOf(">")+1;//获取值开始的坐标
		return xmlContext.substring(length,xmlContext.length()-length-1);
	}
	
	/**
	 * 简单判断字符是否符合xml头部特征
	 * @param headerStr
	 * @return
	 */
	private static boolean isOrNotXmlHeader(String headerStr) {
		return headerStr.contains("?xml")&&headerStr.contains("encoding=")
				&&headerStr.contains("version=")&&headerStr.matches("^[A-Za-z0-9-_.=\"?]+$");
	}
	
	/**
	 * 判断是否符合<xxx></xxx>格式
	 * @param xmlContext
	 * @return
	 */
	private static boolean isOrNotXml(String xmlContext){
		if(xmlContext!=null&&xmlContext.contains(">")){//是否具备xml的基本格式
			int length = xmlContext.indexOf(">")+1;
			String begin = xmlContext.substring(0,length);//开始标签
			String end = xmlContext.substring(xmlContext.length()-length-1,xmlContext.length());//结尾标签
			//判断结尾标签符合xml格式</xxx>，去掉“/”与开始标签一样
			if(end.length()>3&&end.contains("</")&&begin.equals(end.replace("</", "<"))){
				return true;
			}
		}
		return false;
	}
	
}
