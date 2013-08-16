package com.isoftstone.tyw.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.crypt.Md5;
import com.isoftstone.tyw.entity.auths.Additional;
import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.Card;
import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.pub.License;
import com.isoftstone.tyw.entity.pub.LicenseBatch;
import com.isoftstone.tyw.entity.pub.LicensePayment;
import com.isoftstone.tyw.entity.pub.LicenseProject;
import com.isoftstone.tyw.entity.pub.LicenseUser;
import com.isoftstone.tyw.entity.pub.Receipt;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BizService;
import com.isoftstone.tyw.service.CardLogService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.LicenseService;
import com.isoftstone.tyw.util.HttpSend;
import com.isoftstone.tyw.util.Pager;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class LicenseController {
	
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CardLogService cardLogService;
	@Autowired
	private BizService bizService;
	
	/**
	 * 跳转到序列号登录页
	 * 
	 */
	@RequestMapping(value = "/toLicenseLogin")
	public String toLicenseLogin(){
		return "page/license/home";
	}
	
	/**
	 * 跳转到添加项目组信息页
	 * 
	 */
	@RequestMapping(value = "/toProjectInfo")
	public String toProjectInfo(HttpServletRequest request,HttpServletResponse response,Model model){
		//获取session对象
		HttpSession session = request.getSession();
		//从session中获取license的完整信息
		License license = (License)session.getAttribute("license");
		//根据当前登录的license的id查询对应的项目组信息
		LicenseProject lp = licenseService.getProInfoByLicenseId(license.getId());
		model.addAttribute("lp", lp);
		return "page/license/submit";
	}
	
	/**
	 * 跳转到支付页面
	 * 
	 */
	@RequestMapping(value = "/toPay")
	public String toPay(HttpServletRequest request,HttpServletResponse response,Model model){
		String numId = request.getParameter("numId");
		//定义数组，为了在前台显示10个或者15个用户
		String[] transit10 = {"1","2","3","4","5","6","7","8","9","10"};
		String[] transit15 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		HttpSession session = request.getSession();
		License license = (License)session.getAttribute("license");
		//如果是10个人的license
		if(license.getLicenseType()==10){
			session.setAttribute("transit", transit10);
			session.setAttribute("req", transit10.length);
		//如果是15个人的license
		}else if(license.getLicenseType()==15){
			session.setAttribute("transit", transit15);
			session.setAttribute("req", transit15.length);
		}
		//根据license的id获取此license的完整信息
		License l = licenseService.getLicenseInfoByLicenseId(license.getId());
		String user_id = l.getUserId();
		model.addAttribute("uid", user_id);
		//获取次license的白金用户信息
		LicenseUser lu = licenseService.getUserByLicenseId(license.getId());
		model.addAttribute("lu", lu);
		//查询当前license下创建了多少个用户
		List<LicenseUser> list = licenseService.findUser(license.getId());
		model.addAttribute("listSize", list.size());
		model.addAttribute("licenseType", license.getLicenseType());
		//查询当前license除了白金用户以外的其他黄金用户的信息
		List<LicenseUser> luList = licenseService.getUserByLicenseId1(license.getId());
		model.addAttribute("luList", luList);
		
		model.addAttribute("licenseId", license.getId());
		model.addAttribute("numId", numId);
		
		return "page/license/pay";
	}
	
	/**
	 * 黄金用户信息的回显
	 * 
	 */
	@RequestMapping(value = "/toFind", method = RequestMethod.POST)
	@ResponseBody
	public List<LicenseUser> toFind(HttpServletRequest request,HttpServletResponse response,Model model){
		HttpSession session = request.getSession();
		License license = (License)session.getAttribute("license");
		//根据当前license的id查询创建了多少黄金用户，将用户信息显示到页面上
		List<LicenseUser> luList = licenseService.getUserByLicenseId1(license.getId());
		return luList;
	}
	
	
	
	/**
	 * 根据序列号登录
	 * 
	 */
	@RequestMapping(value = "/licenseLogin")
	public String licenseLogin(@ModelAttribute("License") License license,
			HttpServletRequest request,HttpServletResponse response,Model model){
		HttpSession session = request.getSession();
		//根据用户输入的序列号查询此序列号是否存在
		List<License> list = licenseService.findLicense(license.getLicenseNum());
		License l = new License();
		for(int i = 0; i < list.size(); i++){
			l = list.get(i);
			//将license主键id的值set到license中
			license.setId(l.getId());
			license.setLicenseType(l.getLicenseType());
			license.setLicenseStatus(l.getLicenseStatus());
			license.setActivateDate(l.getActivateDate());
		}
		//如果当前输入的序列号存在
		if(list != null && list.size()>0){
			//如果交费时间为空，说明次序列号还没有交费
			if(l.getActivateDate()==null||l.getActivateDate().equals("")){
				//更新序列号的状态为1，表示已登录但是未交费
				licenseService.modifyLicenseStatus("1", license.getId());
				license.setLicenseStatus(1);
			}
			//将license信息放到session中
			session.setAttribute("license", license);
			return "forward:/toProjectInfo";
		}else{
			PrintWriter out = null;
			try{
				//设置除出编码格式
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				//获取out对象
				out = response.getWriter();
				//输出提示信息
				out.println("<script>alert('您输入的序列号无效或不存在！'); window.history.go(-1);</script>");
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(out != null){
					out.close();
					out = null;
				}
			}
			return null;
		}
	}
	
	/**
	 * 支付操作
	 * 1.获取license
	 * 2.获取当前用户手机号列表
	 * 3.获取白金用户
	 * 4.调用支付接口  返回支付状态
	 * 5.成功：后调用短信接口给2中用户发短信通知其已经缴费，失败：返回一个支付失败的页面
	 *   5.1支付成功：购买天佑卡 为 所有用户充值，充值 成功后 再去发短信通知用户充值成功
	 *   5.2支付失败：跳转到一个支付页面并把支付错误信息提示出来 
	 * 6.查询支付历史记录 并跳转到该页面
	 * 
	 * ****这里主要是负责处理用户发票问题，和跳转到一个易支付介绍页面
	 **/
	@RequestMapping(value = "/payAllUser",method = RequestMethod.POST)
	public String payAllUser(LicensePayment payment,HttpServletRequest request, Model model){
		String licenseNumber = request.getParameter("licenseNumber");
		model.addAttribute("licenseNumber", licenseNumber);
		//根据license去获取白金用户
		List<License> licencelist = licenseService.findLicense(licenseNumber);
		if(licencelist.size()>0){
			License license = licencelist.get(0);
			String userId = license.getUserId();
			User wguser =  accountService.getUserById(userId);
			//设置提供第三方接口的参数
			sendPay(wguser,request,model,license);
		}
		//获取需要交易的信息
		String page = "page/license/send";
		String isReceipt = request.getParameter("isReceipt");
		if(StringUtils.equals("1", isReceipt)){
			//这里获取发票信息并展示
			Receipt receipt = licenseService.getReceipt(licenseNumber);
			if(receipt == null){
				receipt = new Receipt();
			}
			model.addAttribute("receipt",receipt);
			page = "page/license/sendforreceipt";
		}
		return page;
	}
	
	/**
	 * 保存 交易信息
	 * @param payment
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/license/savePayment", method = RequestMethod.POST )
	@ResponseBody
	public String savePayment(LicensePayment payment,Model model,HttpServletRequest request) {
		String result = "0";
		String licenseNumber = request.getParameter("licenseNumber");
		//根据license去获取白金用户
		List<License> licencelist = licenseService.findLicense(licenseNumber);
		Double amount = null;
		if(licencelist.size()>0){
			License license = licencelist.get(0);
			payment.setLicenseId(license.getId());
			//填写金额
			amount = new Double(license.getLicenseType()*240);
			payment.setPayAmount(amount+"");
			payment.setIsReceipt(0);//0 不需要发票
			payment.setPayDate(null);
			payment.setUserId(license.getUserId());
			licenseService.save(payment); 
		}
		if(StringUtils.isNotBlank(payment.getId())){
			result = "1";
		}
		return result;
	}
	
	/**
	 * 保存发票信息
	 * @param receipt
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/license/saveReceipt", method = RequestMethod.POST )
	@ResponseBody
	public String saveReceipt(Receipt receipt,Model model,HttpServletRequest request) {
		String result = "0";
		String licenseNumber = request.getParameter("licenseNumber");
		//根据license去获取白金用户
		List<License> licencelist = licenseService.findLicense(licenseNumber);
		Double amount = null;
		LicensePayment payment = new LicensePayment();
		if(licencelist.size()>0){
			License license = licencelist.get(0);
			payment.setLicenseId(license.getId());
			//填写金额
			amount = new Double(license.getLicenseType()*240);
			payment.setPayAmount(amount+"");
			payment.setIsReceipt(1);//1 需要发票
			payment.setPayDate(null);
			payment.setvOid(request.getParameter("vOid"));
			payment.setUserId(license.getUserId());
			licenseService.save(payment); 
		}
		receipt.setLicenseNumber(licenseNumber);
		receipt.setReceiptAmount(amount);
		licenseService.saveReceipt(receipt);
		receipt = licenseService.getReceipt(licenseNumber);
		if(StringUtils.isNotBlank(receipt.getId())&&receipt.getId().length()>1){
			result = "1";
		}
		return result;
	}
	
	/**
	 * 
	 * @param user
	 * @param request
	 * @param model
	 * @param license
	 */
	private void sendPay(User user,HttpServletRequest request, Model model,License license){
		  
		if(user == null){
			return;
		}
		  String v_mid = "6807";		//商户编号，签约由易支付分配。 6802标准
		  String ddate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		  String ddate1= new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		  //订单编号 licenseNumber - ddate
		  String v_oid =ddate1 +"-"+v_mid+"-"+license.getLicenseNum()+"-"+ddate;
		  String v_rcvname = user.getId(); //收货人姓名，建议用商户编号代替。
		  String v_rcvaddr = user.getAddr();//收货人姓名，可以用常量
		  String v_rcvtel = user.getPhone();//收货人电话，可以用常量
		  String v_rcvpost = user.getZip(); //收货人邮编，可以用常量
		  String v_amount = ""; //订单金额
		  if(license.getLicenseType() == 10){
			  v_amount = "2400";
		  }else if(license.getLicenseType() ==15){
			  v_amount = "3600";
		  } 
		  v_amount = "0.01";
		  
		  String v_ymd = ddate1;        //订单日期
		  String v_orderstatus = "1";		//配货状态，0-未配齐，1-已配齐。
		  String v_ordername = "软通动力信息技术有限公司";  //订货人姓名，可以用常量
		  String v_moneytype = "0";  //币种。0-人民币，1-美元。
		  PropertiesReader preader = PropertiesReader.getInstance();
	      String v_url=preader.getProperties("callbackUrl");
//		  String v_url="http://211.141.29.46/payCallback";  //支付完成后返回地址。此地址是支付完成后，订单信息实时的向这个地址做返回。 
//		  String v_url ="http://localhost:8080/payCallback";
		  String MD5Key="test";  // 6802 标准 tian88you88wang8   6807 test
		  //MD5算法
		  Md5 md5 = new Md5 ("") ;
		  String text = v_moneytype+v_ymd+v_amount+v_rcvname+v_oid+v_mid+v_url;
		  String digestString = "";
		  try {
			md5.hmac_Md5(text, MD5Key);
			byte b[]= md5.getDigest();
		    digestString = md5.stringify(b) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		model.addAttribute("v_mid", v_mid);
		model.addAttribute("v_oid", v_oid);
		model.addAttribute("v_rcvname", v_rcvname);
		model.addAttribute("v_rcvaddr", v_rcvaddr);
		model.addAttribute("v_rcvtel", v_rcvtel);
		model.addAttribute("v_rcvpost", v_rcvpost);
		model.addAttribute("v_amount", v_amount);
		model.addAttribute("v_ymd", v_ymd);
		model.addAttribute("v_orderstatus", v_orderstatus);
		model.addAttribute("v_ordername", v_ordername);
		model.addAttribute("v_moneytype", v_moneytype);
		model.addAttribute("v_url", v_url);
		model.addAttribute("digestString", digestString);
 
	}
	
	
	/**
	 * 付款完成回调方法
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payCallback")
	public String payCallback(HttpServletRequest request, HttpServletResponse response,Model model){
		 
		String page = "paysuccess.jsp";
		String queryString = request.getQueryString();
		String query[] = queryString.split("&");
		Map<String,String> map = new HashMap<String,String>();
		for(String param:query){
			int index = param.indexOf("=");
			String key = param.substring(0,index);
			String value = param.substring(index+1);
			map.put(key, value);
		}
		
		PropertiesReader preader = PropertiesReader.getInstance();
		String payCode=preader.getProperties("thirdPayCode");
		String keyCode=preader.getProperties("thirdKeyCode");
		String v_oid = map.get("v_oid");
		String v_pmode = map.get("v_pmode");
		String v_pstatus = map.get("v_pstatus");
		String v_pstring = map.get("v_pstring");
		String v_md5info = map.get("v_md5info");
		String v_amount = map.get("v_amount");
		String v_moneytype = map.get("v_moneytype");
		String v_md5money = map.get("v_md5money");
		 
		/**
		 * 　由于非对称安全验证方式使用时需要有安装证书文件等操作，如果是虚拟主机用户鉴于条件所限无法实现，则还可以使用原来md5验证方式。
		 */
		String v_sign = map.get("v_sign");
		
		try {
			v_pmode = URLDecoder.decode(v_pmode,"GB2312");
			v_pstring = URLDecoder.decode(v_pstring,"GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//采用md5加密方式 来验证
		String md5info = v_oid+v_pstatus+v_pstring+v_pmode; //v_oid，v_pstatus，v_pstring和v_pmode
		String md5money =v_amount+v_moneytype; //v_amount，v_moneytype
		Md5 md5 = new Md5 ("") ;
		Md5 md  = new Md5("");
		try{
			md5.hmac_Md5(md5info ,keyCode) ;
			byte c[]= md5.getDigest();
			String infoString = md5.stringify(c) ;
			md.hmac_Md5(md5money ,keyCode) ;
			byte b[]= md.getDigest();
			String moneyString = md.stringify(b) ;
			/**
			 * 1.将返回的订单信息进行持久化
			 * 2.在本系统中 购买天佑卡 为 所有用户充值，充值 成功
			 * 3.发短信通知用户充值成功
			 * */
			if(StringUtils.equals("20", v_pstatus)&&StringUtils.equals(infoString, v_md5info)&&StringUtils.equals(moneyString, v_md5money)){
				License license = null;
				 int index = v_oid.indexOf(payCode+"-");
				 /**
				   * 1.获取包年卡类型
				   * 2.生成一张天佑卡
				   * 3.生成台帐表记录
				   * */
				 if(index>0){
					  String licenseNumber = v_oid.substring(index+5,index+5+19);//商户标号是4位+“-”
					  List<License> list = licenseService.findLicense(licenseNumber);
					  license = list.get(0);
					  license.setActivateDate(new Date());
					  license.setLicenseStatus(2);//改成已经缴费
					  
					  //这里需要讲交易信息持久化到数据库（只要验证通过就把信息写入数据库 确保订单信息正确性）
					  List<LicensePayment> paylist = licenseService.listLicensePayment(license.getId());
					  LicensePayment payment = paylist.get(0);
					  payment.setUserId(license.getUserId());
					  payment.setPayStatus(v_pstatus);
					  payment.setPayAmount(v_amount);
					  payment.setvOid(v_oid);
					  payment.setvPmode(v_pmode);
					  payment.setvPstring(v_pstring);
					  payment.setvMd5info(v_md5info);
					  payment.setvMd5money(v_md5money);
					  payment.setvMoneytype(v_moneytype);
					  payment.setPayDate(new Date());
					  licenseService.save(payment);//将交易信息持久化
					  
					  //获取该license下的所有用户并且充值
					  List<LicenseUser> userlist = licenseService.findLicenseUserList(licenseNumber);
					  CardType cardType = cardLogService.getCardType(12);
					  Card card = new Card();
					  UUID uuid = UUID.randomUUID();
					  card.setCardNo(uuid.toString());
					  card.setCardTypeId(cardType.getId());
					  card.setActiveTime(new Date());
//					  card.setPrice(new Double(v_amount));
					  card.setPrice(new Double(3600));
					  card.setStatus(2);
					  card.setAuthsUserId(license.getUserId());
					  card.setCreateId("systemUser");
					  bizService.saveCard(card);//生成卡
					  
					  /**
					   * 往台帐表里填写一条数据
					   * 1.先获取当前license的白金用户帐号
					   * 2.获取白金帐号的最后一条消费记录
					   * 3.创建白金帐号的新的消费记录
					   * 如果是普通用户的话，需要参考白金用户帐号
					   * */
					  
					  CardLog goldLog = null;
					  for(LicenseUser lu:userlist){
						  if(StringUtils.equals(lu.getUserId(),license.getUserId())){
							  //先完成白金帐号的记账行为
							  CardLog log = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);
							 
							  if(log == null){
								  //System.out.println("gold log is null");
								  CardLog cl = new CardLog();
								  cl.setAuthsUserId(license.getUserId());
								  cl.setBizCardId(card.getId());
								  cl.setBizType(0);
								  cl.setDataDate(new Date());
								  cl.setInsertName("系统充值");
								  cl.setLastTotal(0);
								  cl.setOverTotal(365);
								  cl.setStatus(0);
								  cl.setSummary("");
								  cl.setValue(0);
								  bizService.saveCardLog(cl);
							  }else{
								  //System.out.println("gold log is not null");
								  CardLog cl = new CardLog();
								  cl.setAuthsUserId(license.getUserId());
								  cl.setBizCardId(card.getId());
								  cl.setBizType(0);
								  cl.setDataDate(new Date());
								  cl.setInsertName("系统充值");
								  cl.setLastTotal(log.getOverTotal());//上期结余
								  cl.setOverTotal(log.getOverTotal()+365);//本期结余
								  cl.setStatus(1);
								  cl.setSummary("");
								  cl.setValue(log.getValue());//消费点数
								  bizService.saveCardLog(cl);
							  }
							  card.setPrice(card.getPrice()-240);
							  bizService.saveCard(card);//生成完成白金用户台帐后要扣除240元
							  goldLog = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);//这里重新获取记录
						  }
					  }
					  for(LicenseUser lu:userlist){
						  if(!StringUtils.equals(lu.getUserId(),license.getUserId())){
							  //复制白金帐号最后一条记录
							  CardLog log = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);
							  //判断是否已经存在这个用户的消费记录，如果存在累加，不存在则创建
							  if(log == null){
								  CardLog cl = new CardLog();
								  cl.setAuthsUserId(lu.getUserId());
								  cl.setBizCardId(card.getId());
								  cl.setBizType(0);
								  cl.setDataDate(new Date());
								  cl.setInsertName("系统充值");
								  cl.setLastTotal(goldLog.getLastTotal());
								  cl.setOverTotal(goldLog.getOverTotal());
								  cl.setStatus(1);
								  cl.setSummary("");
								  cl.setValue(goldLog.getValue());
								  bizService.saveCardLog(cl);
							  }else{
								  CardLog cl = new CardLog();
								  cl.setAuthsUserId(lu.getUserId());
								  cl.setBizCardId(card.getId());
								  cl.setBizType(0);
								  cl.setDataDate(new Date());
								  cl.setInsertName("系统充值");
								  cl.setLastTotal(log.getOverTotal());//上期结余
								  cl.setOverTotal(log.getOverTotal()+365);//本期结余
								  cl.setStatus(1);
								  cl.setSummary("");
								  cl.setValue(log.getValue());//消费点数
								  bizService.saveCardLog(cl);
							  }
							  card.setPrice(card.getPrice()-240);
							  bizService.saveCard(card);//生成完成普通用户台帐后要扣除240元
							  User usr = accountService.getUserById(lu.getUserId());
							  usr.setGrade(1); //将用户等级设置撑黄金vip
							  accountService.updateUserNoPassword(usr);
						  }
					  } 
					 
					  //持久化订单信息
					  licenseService.saveOne(license);
					  
				 }else{
					 //处理失败 订单编号不正确
					 model.addAttribute("message", URLEncoder.encode("验证成功，处理失败"));
					 page = "/payfail.jsp";
				 }
			}else{
				/**
				 * 1.判断支付状态 
				 * 2.成功 提示非法操作
				 * 3.失败 跳转到失败页面
				 * 跳转到一个支付页面并把支付错误信息提示出来
				 * */
				if(StringUtils.equals("20", v_pstatus)){
					model.addAttribute("message", URLEncoder.encode("您交易已经成功，但出现了非法操作，请您联系我们管理人员"));
				}else if(StringUtils.equals("30", v_pstatus)){
					model.addAttribute("message", v_pstring);
				}
				page = "/payfail.jsp";
			}
		}catch(Exception ex){
			model.addAttribute("message", ex);
			page = "/payfail.jsp";
		}
		return "redirect:"+page;
	}
	
 
	/**
	 * 支付延时处理结果
	 * 1.接受参数 并且验证
	 * 2.判断该单是否已经被处理
	 * 3.发现没有被处理的话，再次进行处理
	 * 4.处理成功 返回 sent  失败 返回 error
	 * @param request
	 * @param model
	 * @return
	 */ 
	@RequestMapping(value = "/callbackTimeDelay")
	public String callbackTimeDelay(HttpServletRequest request,HttpServletResponse response, Model model){
		//System.out.println("进入第三方延时返回接口处理方法");
		String result = "1";
		
		String queryString = request.getQueryString();
		String query[] = queryString.split("&");
		Map<String,String> map = new HashMap<String,String>();
		for(String param:query){
			int index = param.indexOf("=");
			String key = param.substring(0,index);
			String value = param.substring(index+1);
			map.put(key, value);
		}
		
		PropertiesReader preader = PropertiesReader.getInstance();
		String payCode=preader.getProperties("thirdPayCode");
		String keyCode=preader.getProperties("thirdKeyCode");
		String v_oid = map.get("v_oid");
		String v_pmode = map.get("v_pmode");
		String v_pstatus = map.get("v_pstatus");//0→待处理（支付结果未确定）；  1支付完成；3支付被拒绝
		String v_pstring = map.get("v_pstring");
		String v_amount = map.get("v_amount");
		String v_moneytype = map.get("v_moneytype");
		String v_count = map.get("v_count");
		String v_md5money = map.get("v_md5money");
		String v_mac = map.get("v_mac");
		String v_sign = map.get("v_sign");
		
		/**
		 * 　由于非对称安全验证方式使用时需要有安装证书文件等操作，如果是虚拟主机用户鉴于条件所限无法实现，则还可以使用原来md5验证方式。
		 */
		
		try {
			v_pmode = URLDecoder.decode(v_pmode,"GB2312");
			v_pstring = URLDecoder.decode(v_pstring,"GB2312");
			v_pstatus = URLDecoder.decode(v_pstatus,"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//采用md5加密方式 来验证
		String mac = v_oid+v_pmode+v_pstatus+v_pstring+v_count;//v_oid+v_pmode+v_pstatus+v_pstring+v_count
		String md5money =v_amount+v_moneytype; //v_amount，v_moneytype
		Md5 md5 = new Md5 ("") ;
		Md5 md  = new Md5("");
		try{
			md5.hmac_Md5(mac ,keyCode) ;
			byte c[]= md5.getDigest();
			String infoString = md5.stringify(c) ;
			md.hmac_Md5(md5money ,keyCode) ;
			byte b[]= md.getDigest();
			String moneyString = md.stringify(b) ;
			//System.out.println("将要验证");
			//System.out.println(infoString+":"+v_mac);
			//System.out.println(moneyString+":"+v_md5money);
			if(StringUtils.equals(infoString, v_mac)
					&&StringUtils.equals(moneyString, v_md5money)){
				//System.out.println("验证成功:"+v_oid);
			//验证成功  判断订单是否已经处理
			String oid []  = v_oid.split("[|][_][|]");
			for(int i = 0 ;i<oid.length;i++){
				//System.out.println("oid:"+oid[i]);
				LicensePayment pay = licenseService.findLicensePaymentByvOid(oid[i]);
				//System.out.println("XX:"+pay);
				if(pay!=null&&StringUtils.equals(pay.getPayStatus(),"20")){//如果已经支付成功
					//System.out.println("支付成功了，就不要再试了");
					continue;
				}
				 
				if(pay==null||pay.getPayStatus()==null||"".equals(pay.getPayStatus())){//没有处理状态就是未处理
					
					License license = null;
					int index = oid[i].indexOf(payCode+"-");
					 /**
					   * 1.获取包年卡类型
					   * 2.生成一张天佑卡
					   * 3.生成台帐表记录
					   * */
					 if(index>0){
//						  String licenseNumber = oid[i].substring(index+5);//商户标号是4位+“-”
						  String licenseNumber = oid[i].substring(index+5,index+5+19);//商户标号是4位+“-”
						  List<License> list = licenseService.findLicense(licenseNumber);
						  if(list.size()<1||list.get(0)==null){
							  //System.out.println("license号码出现问题，订单编号为："+oid[i]);
							  continue;
						  }
						  license = list.get(0);
						  license.setActivateDate(new Date());
						  license.setLicenseStatus(2);//改成已经缴费
						 
						  //获取该license下的所有用户并且充值
						  List<LicenseUser> userlist = licenseService.findLicenseUserList(licenseNumber);
						  CardType cardType = cardLogService.getCardType(12);
						  Card card = new Card();
						  UUID uuid = UUID.randomUUID();
						  card.setCardNo(uuid.toString());
						  card.setCardTypeId(cardType.getId());
						  card.setActiveTime(new Date());
						  card.setPrice(new Double(v_amount));
						  card.setStatus(2);
						  card.setAuthsUserId(license.getUserId());
						  card.setCreateId("systemUser");
						  bizService.saveCard(card);//生成卡
						  
						  CardLog goldLog = null;
						  for(LicenseUser lu:userlist){
							  if(StringUtils.equals(lu.getUserId(),license.getUserId())){
								  //System.out.println("白金用户处理");
								  //先完成白金帐号的记账行为
								  CardLog log = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);
								 
								  if(log == null){
									  //System.out.println("gold log is null");
									  CardLog cl = new CardLog();
									  cl.setAuthsUserId(license.getUserId());
									  cl.setBizCardId(card.getId());
									  cl.setBizType(0);
									  cl.setDataDate(new Date());
									  cl.setInsertName("系统充值");
									  cl.setLastTotal(0);
									  cl.setOverTotal(365);
									  cl.setStatus(0);
									  cl.setSummary("");
									  cl.setValue(0);
									  bizService.saveCardLog(cl);
								  }else{
									  //System.out.println("gold log is not null");
									  CardLog cl = new CardLog();
									  cl.setAuthsUserId(license.getUserId());
									  cl.setBizCardId(card.getId());
									  cl.setBizType(0);
									  cl.setDataDate(new Date());
									  cl.setInsertName("系统充值");
									  cl.setLastTotal(log.getOverTotal());//上期结余
									  cl.setOverTotal(log.getOverTotal()+365);//本期结余
									  cl.setStatus(1);
									  cl.setSummary("");
									  cl.setValue(log.getValue());//消费点数
									  bizService.saveCardLog(cl);
								  }
								  card.setPrice(card.getPrice()-240);
								  bizService.saveCard(card);//生成完成白金用户台帐后要扣除240元
								  goldLog = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);//这里重新获取记录
							  }
						  }
						  //普通用户
						  for(LicenseUser lu:userlist){
							  
							  if(!StringUtils.equals(lu.getUserId(),license.getUserId())){
								  //System.out.println("普通用户处理");
								  //复制白金帐号最后一条记录
								  CardLog log = cardLogService.getCardLogByUserIdAndType(lu.getUserId(),0);
								  //判断是否已经存在这个用户的消费记录，如果存在累加，不存在则创建
								  if(log == null){
									  CardLog cl = new CardLog();
									  cl.setAuthsUserId(lu.getUserId());
									  cl.setBizCardId(card.getId());
									  cl.setBizType(0);
									  cl.setDataDate(new Date());
									  cl.setInsertName("系统充值");
									  cl.setLastTotal(goldLog.getLastTotal());
									  cl.setOverTotal(goldLog.getOverTotal());
									  cl.setStatus(1);
									  cl.setSummary("");
									  cl.setValue(goldLog.getValue());
									  bizService.saveCardLog(cl);
								  }else{
									  CardLog cl = new CardLog();
									  cl.setAuthsUserId(lu.getUserId());
									  cl.setBizCardId(card.getId());
									  cl.setBizType(0);
									  cl.setDataDate(new Date());
									  cl.setInsertName("系统充值");
									  cl.setLastTotal(log.getOverTotal());//上期结余
									  cl.setOverTotal(log.getOverTotal()+365);//本期结余
									  cl.setStatus(1);
									  cl.setSummary("");
									  cl.setValue(log.getValue());//消费点数
									  bizService.saveCardLog(cl);
								  }
								  card.setPrice(card.getPrice()-240);
								  bizService.saveCard(card);//生成完成普通用户台帐后要扣除240元
								  User usr = accountService.getUserById(lu.getUserId());
								  usr.setGrade(1); //将用户等级设置撑黄金vip
								  accountService.updateUserNoPassword(usr);
							  }
						  } 
						  //这里需要讲交易信息持久化到数据库
						  List<LicensePayment> paylist = licenseService.listLicensePayment(license.getId());
						  LicensePayment payment = paylist.get(0);
						  payment.setUserId(license.getUserId());
						  payment.setPayAmount(v_amount.split("[|][_][|]")[i]);
						  payment.setPayStatus("20");
						  payment.setvOid(oid[i]);
						  payment.setvPmode(v_pmode.split("[|][_][|]")[i]);
						  payment.setvPstring(v_pstring.split("[|][_][|]")[i]);
						  payment.setvMd5money(v_md5money);
						  payment.setvMoneytype(v_moneytype.split("[|][_][|]")[i]);
						  payment.setPayDate(new Date());
						  licenseService.save(payment);//将交易信息持久化
						  //持久化订单信息
						  licenseService.saveOne(license);
						  
						  response.getWriter().write("sent");
					 }else{
						  //System.out.println("编号不正确");
						  result = "2";
						  //将返回内容入库
						  response.getWriter().write("error");
					 }
				}
			} 
					if(!StringUtils.equals(result, "2")){
						 response.getWriter().write("sent");
					}
			}else{
				//System.out.println("验证不通过，不予以处理");
				response.getWriter().write("error");
			}
		}catch(Exception ex){
			//System.out.println("处理异常");
			try {
				response.getWriter().write("error");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
		//System.out.println("第三方延时返回接口处理方法结束");
		return null;
	}
	
	/**
	 * 添加项目组信息
	 */
	@RequestMapping(value="/addProjectInfo")
	public String addProjectInfo(@ModelAttribute("LicenseProject") LicenseProject licenseProject,
			HttpServletRequest request,HttpServletResponse response,Model model){
		String[] transit10 = {"1","2","3","4","5","6","7","8","9","10"};
		String[] transit15 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		//获取session中license信息
		HttpSession session = request.getSession();
		License license = (License)session.getAttribute("license"); 
		//获取license的id值
		String licenseId = license.getId();
		licenseProject.setLicenseId(licenseId);
		//保存项目组信息
		licenseService.save(licenseProject);
		//如果当前license为十用户，在页面上显示十个创建用户的位置
		if(license.getLicenseType()==10){
			session.setAttribute("transit", transit10);
			session.setAttribute("req", transit10.length);
		//如果当前license为十五用户，在页面上显示十五个创建用户的位置
		}else if(license.getLicenseType()==15){
			session.setAttribute("transit", transit15);
			session.setAttribute("req", transit15.length);
		}
		return "forward:/toPay";
	}

	/**
	 * 进入license管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/licenselist", method = RequestMethod.GET)
	public String licenselist(HttpServletRequest request, Model model) {
		return "page/license/licenselist";
	}
	
	/**
	 * 分页刷新license列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/licenselist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> licenselistTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String licenseNum=request.getParameter("licenseNum");
		String batchCode=request.getParameter("batchCode");
		String status = request.getParameter("status");
		
		Page<License> page = licenseService.listLicense(batchCode, licenseNum,status,pageable);
		result.put("total",page.getTotalElements());  
		result.put("rows", page.getContent());
		return result;
	}
	
	
	/**
	 * 分页刷新license列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/licensebatchlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> licensebatchlistTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String batchCode=request.getParameter("licenseBatch");
		String agentId=request.getParameter("agentId");
		//Page<LicenseBatch> page = licenseService.listLicenseBatch(agentId, batchCode,pageable);
		List<LicenseBatch> licenseBatchList = licenseService.listLicenseBatchBaseRows(batchCode, agentId, pageable);
		BigInteger licenseBatchTotal = licenseService.listLicenseBatchBaseTotal(batchCode, agentId);
		result.put("total",licenseBatchTotal);  
		result.put("rows", licenseBatchList);
		return result;
	}
	
	/**
	 * 获取渠道商列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qdslist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> qdslistTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		Pager page = accountService.listUser(null,null,3,null,null,pageable);
		result.put("total",page.getRowCount());  
		result.put("rows", page.getResult());  
		return result;
	}
	
	
	/**
	 * 生成序列号
	 * 1.生成批次号
	 * 2.生成序列号
	 * @param license
	 * @param response
	 */
	@RequestMapping(value = "/license/save", method = RequestMethod.POST )
	@ResponseBody
	public void doSave(LicenseBatch batch,HttpServletResponse response,HttpServletRequest request){
		String resultJson = "{'success':true}";
		try {
			String agentId = request.getParameter("agentId");
			if(StringUtils.isNotBlank(agentId)){
				User u =  new User();
				u.setId(agentId);
				batch.setAgent(u);
				batch.setLicenseTotal(batch.getLicenseFifTotal()+batch.getLicenseTenTotal());
				List<LicenseBatch> batchlist = licenseService.listLicenseBatch();
				int batchCode = 100;
				if(batchlist != null && batchlist.size()>0){
					LicenseBatch bb = batchlist.get(0);
					batchCode = (Integer.parseInt(bb.getBatchCode())+1);
				} 
				batch.setBatchCode(batchCode+"");
				licenseService.save(batch);
				List<License> list = generateLicense(batch);
				licenseService.saveAll(list);
			}
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	
	/**
	 * 根据渠道商ID和数量来生成 license
	 * @param agentId
	 * @param total
	 * @return
	 * @throws InterruptedException
	 */
	private List<License> generateLicense(LicenseBatch batch) throws InterruptedException{
		List<License> list = new ArrayList<License>();
		for(int i = 0 ;i<batch.getLicenseFifTotal();i++){
			License license = new License();
			license.setLicenseStatus(0);
			license.setLicenseType(15);
			license.setBatchCode(batch.getBatchCode());
			Thread.sleep(15);
			StringBuffer   licenseNum   =   new   StringBuffer(System.currentTimeMillis()+""+(int)(Math.random()*899+100));
			licenseNum = new StringBuffer(md5(licenseNum.toString()));
			licenseNum.insert(4, "-");
			licenseNum.insert(9, "-");
			licenseNum.insert(14, "-");
			license.setLicenseNum(licenseNum.toString());
			list.add(license);
		}
		for(int i = 0 ;i<batch.getLicenseTenTotal();i++){
			License license = new License();
			license.setLicenseStatus(0);
			license.setLicenseType(10);
			license.setBatchCode(batch.getBatchCode());
			Thread.sleep(15);
			StringBuffer   licenseNum   =   new   StringBuffer(System.currentTimeMillis()+""+(int)(Math.random()*899+100));
			licenseNum = new StringBuffer(md5(licenseNum.toString()));
			licenseNum.insert(4, "-");
			licenseNum.insert(9, "-");
			licenseNum.insert(14, "-");
			license.setLicenseNum(licenseNum.toString());
			list.add(license);
		}
		return list;
	}
	
	/**
	 * md5加密
	 * @param plainText
	 * @return
	 */
	private String md5(String plainText) {
		PropertiesReader preader = PropertiesReader.getInstance();
		String md5key=preader.getProperties("md5key");
		plainText+=md5key;
		String str = "";
			  try {
			   MessageDigest md = MessageDigest.getInstance("MD5");
			   md.update(plainText.getBytes("UTF-8"));
			   byte b[] = md.digest();
			   int i;
			   StringBuffer buf = new StringBuffer("");
			   for (int offset = 0; offset < b.length; offset++) {
				    i = b[offset];
				    if (i < 0){
				    	i += 256;
				    }
				    if (i < 16){
				    	buf.append("0");
				    }
				    buf.append(Integer.toHexString(i));
			   }
				   str =  buf.toString().substring(8, 24);
			  } catch (NoSuchAlgorithmException e) {
				  e.printStackTrace();
			  } catch (UnsupportedEncodingException e) {
				  e.printStackTrace();
			}
			  return str;
		}
	
	/**
	 * 前台支付时创建用户
	 */
	@RequestMapping(value="/createUserList")
	public String createUserList(HttpServletRequest request,HttpServletResponse response,Model model){
		String[] transit10 = {"1","2","3","4","5","6","7","8","9","10"};
		String[] transit15 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		String tel = "";
		String name = "";
		HttpSession session = request.getSession();
		License license = (License)session.getAttribute("license");
		
		//如果是10个人的license
		if(license.getLicenseType()==10){
			for(int i = 1; i<=transit10.length; i++){
				//从前台获取电话号码
				tel = request.getParameter("phone"+i);
				//从前台获取姓名
				name = request.getParameter("name"+i);
				
				
				String licenseUserId = request.getParameter("licenseUserId"+i);
				List<LicenseUser> plist = licenseService.getUserByPhone(license.getId(), tel);
				List<LicenseUser> ulist = licenseService.getUserByPhoneAndName(license.getId(), name, tel);
				if(ulist.size()>0){
					continue;
				}else if(ulist.size()==0&&plist.size()>0){
					licenseService.modifyLicenseUserName(name, tel, license.getId());
				}else if(tel==null || name==null){
					continue;
				}else if(tel=="Phone"&&name=="Name"){
					continue;
				}else if(tel != null && !tel.equals("Phone") && name != null && !name.equals("Name")){
					//实例化user对象
					User user = new User();
					user.setUsername("");
					user.setPassword("123456");
					user.setUserType(2);
					user.setNonLocked(true);
					user.setEnable(true);
					user.setPhone(tel);
					//保存用户
					accountService.saveUser(user);
					//实例化licenseUser对象
					LicenseUser licenseUser = new LicenseUser();
					licenseUser.setLicenseId(license.getId());
					licenseUser.setPhone(tel);
					licenseUser.setUserId(user.getId());
					licenseUser.setUserName(name);
					//保存licenseUser
					licenseService.saveLicenseUser(licenseUser);
					
					if(i==1){
						licenseService.modifyUserId(user.getId(), license.getId());
						licenseService.modifyUserGrade(2, user.getId());
					}else{
						licenseService.modifyUserGrade(1, user.getId());
					}
					if(licenseUserId!=null && !licenseUserId.equals("")){
						licenseService.deleteLicenseUserId(licenseUserId);
					}
				}
			}
			
			PrintWriter out = null;
			try{
				//设置除出编码格式
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				//获取out对象
				out = response.getWriter();
				//输出提示信息
				out.println("<script>alert('全部发送成功！'); window.location.href='/toPay';</script>");
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(out != null){
					out.close();
					out = null;
				}
			}
			return null;
			
		//如果是15个人的license
		}else if(license.getLicenseType()==15){
			for(int j = 1; j<=transit15.length; j++){
				//从前台获取电话号码
				tel = request.getParameter("phone"+j);
				//从前台获取姓名
				name = request.getParameter("name"+j);
				
				
				String licenseUserId = request.getParameter("licenseUserId"+j);
				List<LicenseUser> plist = licenseService.getUserByPhone(license.getId(), tel);
				List<LicenseUser> ulist = licenseService.getUserByPhoneAndName(license.getId(), name, tel);
				if(ulist.size()>0){
					continue;
				}else if(ulist.size()==0&&plist.size()>0){
					licenseService.modifyLicenseUserName(name, tel, license.getId());
				}else if(tel==null || name==null){
					continue;
				}else if(tel=="Phone"&&name=="Name"){
					continue;
				}else if(tel != null && !tel.equals("Phone") && name != null && !name.equals("Name")){
					//实例化user对象
					User user = new User();
					user.setUsername("");
					user.setPassword("123456");
					user.setUserType(2);
					user.setNonLocked(true);
					user.setEnable(true);
					user.setPhone(tel);
					//保存用户
					accountService.saveUser(user);
					//实例化licenseUser对象
					LicenseUser licenseUser = new LicenseUser();
					licenseUser.setLicenseId(license.getId());
					licenseUser.setPhone(tel);
					licenseUser.setUserId(user.getId());
					licenseUser.setUserName(name);
					//保存licenseUser
					licenseService.saveLicenseUser(licenseUser);
					
					if(j==1){
						licenseService.modifyUserId(user.getId(), license.getId());
						licenseService.modifyUserGrade(2, user.getId());
					}else{
						licenseService.modifyUserGrade(1, user.getId());
					}
					if(licenseUserId!=null && !licenseUserId.equals("")){
						licenseService.deleteLicenseUserId(licenseUserId);
					}
				}
			}
			
			PrintWriter out = null;
			try{
				//设置除出编码格式
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				//获取out对象
				out = response.getWriter();
				//输出提示信息
				out.println("<script>alert('全部发送成功！'); window.location.href='/toPay';</script>");
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(out != null){
					out.close();
					out = null;
				}
			}
			return null;
			
		}
		return null;
	}
	
	/**
	 * 单独发送时创建用户功能
	 * 
	 */
	@RequestMapping(value = "/addLicenseUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUpload(HttpServletRequest request,HttpServletResponse response,Model model){
		//定义返回结果
		Map<String,Object> result=new HashMap<String,Object>();
		//获取单独发送时前台输入的姓名
		String singlename = request.getParameter("singlename");
		//获取单独发送是前台输入的电话号码
		String singlephone = request.getParameter("singlephone");
		//获取当前单独保存的行数，为单独保存白金用户使用
		String num = request.getParameter("num");
		//获取session中的license信息
		HttpSession session = request.getSession();
		License license = (License)session.getAttribute("license");
		//获取当前单独发送的这条数据的pub_license_user中的主键id
		String licenseUserId = request.getParameter("licenseUserId");
		//根据页面传递过来的手机号去auths_user表中查询该手机号是否存在
		List<User> elist = licenseService.getUserExistByPhone(singlephone);
		//根据手机号码查询一条用户信息
		User u = accountService.loadUserByPhone(singlephone);
		//如果当前license未交费
		if(license.getLicenseStatus()==1){
			//根据licenseId和手机号查询此条记录在pub_license_user表中是否存在
			List<LicenseUser> plist = licenseService.getUserByPhone(license.getId(), singlephone);
			//根据licenseId、手机号和姓名查询此条记录在pub_license_user表中是否存在
			List<LicenseUser> ulist = licenseService.getUserByPhoneAndName(license.getId(), singlename, singlephone);
			//如果输入的手机号存在并且页面传过来的姓名和手机号无更改，就算是重复发送
			if(ulist.size()>0 && elist.size()>0){
				//提示请勿重复发送
				result.put("fail",true);
			//只修改姓名的情况下
			}else if(ulist.size()==0&&plist.size()>0 && elist.size()>0){
				//修改姓名操作
				licenseService.modifyLicenseUserName(singlename, singlephone, license.getId());
				//提示修改成功
				result.put("change",true);
			//如果手机号存在，需要把该手机号在用户表里的信息添加到licenseuser表中
			}else if(elist.size()>0){
				//如果该手机号的等级是普通用户表示该用户可以在未付费的情况下被创建
				if(u.getGrade()==0){
					LicenseUser licenseUser = new LicenseUser();
					licenseUser.setLicenseId(license.getId());
					licenseUser.setPhone(singlephone);
					licenseUser.setUserId(u.getId());
					licenseUser.setUserName(singlename);
					//保存一条licenseUser信息
					licenseService.saveLicenseUser(licenseUser);
					//读取配置文件中允许发送短信的次数
					PropertiesReader pr = PropertiesReader.getInstance();
				    int messageCount=Integer.parseInt(pr.getProperties("messageCount"));
				    //根据当前登录的licenseId查询此条license的信息
				    License li = licenseService.getLicenseInfoByLicenseId(license.getId());
				    //如果当前登录的license发送信息的次数没有达到限制的次数
				    if(li.getOverplusCount()<messageCount){
				    	HttpSend.sendMessage(singlephone, "恭喜您成为天佑网（www.tianyouwang.net）会员，账号：您的手机号，密码：123456");
				    	//给当前登录的license的发送信息次数增加一次
				    	licenseService.modifyOverPlusCount(li.getOverplusCount()+1, license.getId());
				    }
					//如果当前被修改的这条记录在pub_license_user表中存在
					if(licenseUserId!=null && !licenseUserId.equals("")){
						//在pub_license_user表中删除当前被修改的记录
						licenseService.deleteLicenseUserId(licenseUserId);
					}
					//判断当前操作的信息是不是第一条，也就是当前创建的是否是白金用户
					if(num != null && num.equals("1")){
						//把白金用户的id放到对应的license下
						licenseService.modifyUserId(u.getId(), license.getId());
						//修改此白金用户在用户表里的等级字段，2代表白金用户
						licenseService.modifyUserGrade(2, u.getId());
						
						PropertiesReader preader = PropertiesReader.getInstance();
					    int tryOutTimes=Integer.parseInt(preader.getProperties("tryOutTimes"));
						
						CardLog cardTry = new CardLog();
						cardTry.setAuthsUserId(u.getId());
						//addcl.setBizCardId(cl.getBizCardId());
						cardTry.setBizType(0);
						cardTry.setDataDate(new Date());
						cardTry.setInsertName("系统充值");
						cardTry.setLastTotal(0);
						cardTry.setValue(0);
						cardTry.setOverTotal(tryOutTimes);
						cardTry.setStatus(0);
						cardLogService.saveCardLog(cardTry);
						//cardTry.setSummary(cl.getSummary());
						
					}
					//如果当前license发送信息的次数没超过限制
					if(li.getOverplusCount()<messageCount){
						//提示修改用户成功
						result.put("changephone",true);
						//如果当前license发送信息的次数已经超过限制	
					}else if(li.getOverplusCount()>=messageCount){
						//提示修改用户成功，但是无法发送信息
						result.put("changephoneTip",true);
					}
					
				//如果该手机号不是普通用户，就不能被创建或者修改
				}else{
					result.put("tip", true);
				}
			//如果该手机号不存在，而且不是修改操作，执行下列方法
			}else{
				User user = new User();
				Additional addi = new Additional();
				//将用户名默认为手机号
				user.setUsername(singlephone);
				user.setAliasname(singlename);
				//默认密码为123456
				user.setPassword("123456");
				user.setUserType(1);
				user.setStatus("1");
				user.setNonLocked(true);
				user.setEnable(true);
				user.setPhone(singlephone);
				user.setFirstLogin("0");
				addi.setId(user.getId());
				addi.setMobile(singlephone);
				user.setAdditional(addi);
				user.getAdditional().setUser(user);
				Set<Role> rs = new TreeSet<Role>();//声明权限集合
				Role r = accountService.getById("5");//取得个人权限
				rs.add(r);//把个人权限放入权限集合中
				user.setRoles(rs);//把权限集合赋给注册的个人用户
				//保存用户信息
				accountService.saveUser(user);
				
				
				PropertiesReader pr = PropertiesReader.getInstance();
			    int messageCount=Integer.parseInt(pr.getProperties("messageCount"));
			    
			    License li = licenseService.getLicenseInfoByLicenseId(license.getId());
			    if(li.getOverplusCount()<messageCount){
			    	HttpSend.sendMessage(singlephone, "恭喜您成为天佑网（www.tianyouwang.net）会员，账号：您的手机号，密码：123456");
			    	licenseService.modifyOverPlusCount(li.getOverplusCount()+1, license.getId());
			    }
				
				LicenseUser licenseUser = new LicenseUser();
				licenseUser.setLicenseId(license.getId());
				licenseUser.setPhone(singlephone);
				licenseUser.setUserId(user.getId());
				licenseUser.setUserName(singlename);
				licenseService.saveLicenseUser(licenseUser);
				//如果当前执行发送的是第一行，更新license表中用户id的值，说明该用户是白金用户
				if(num != null && num.equals("1")){
					licenseService.modifyUserId(user.getId(), license.getId());
					licenseService.modifyUserGrade(2, user.getId());
					
					PropertiesReader preader = PropertiesReader.getInstance();
				    int tryOutTimes=Integer.parseInt(preader.getProperties("tryOutTimes"));
					
					CardLog cardTry = new CardLog();
					cardTry.setAuthsUserId(user.getId());
					//addcl.setBizCardId(cl.getBizCardId());
					cardTry.setBizType(0);
					cardTry.setDataDate(new Date());
					cardTry.setInsertName("系统充值");
					cardTry.setLastTotal(0);
					cardTry.setValue(0);
					cardTry.setOverTotal(tryOutTimes);
					cardTry.setStatus(0);
					cardLogService.saveCardLog(cardTry);
					//cardTry.setSummary(cl.getSummary());
					
				}
				if(licenseUserId!=null && !licenseUserId.equals("")){
					licenseService.deleteLicenseUserId(licenseUserId);
				}
				if(li.getOverplusCount()<messageCount){
					//提示创建用户成功
					result.put("success",true);
				}else if(li.getOverplusCount()>=messageCount){
					//提示创建用户成功，但是无法发送短信
					result.put("messageTip", true);
				}
				
			}
		//如果当前license已交费
		}else if(license.getLicenseStatus()==2){
			List<LicenseUser> plist = licenseService.getUserByPhone(license.getId(), singlephone);
			List<LicenseUser> ulist = licenseService.getUserByPhoneAndName(license.getId(), singlename, singlephone);
			//如果输入的手机号存在并且页面传过来的姓名和手机号无更改，就算是重复发送
			if(ulist.size()>0 && elist.size()>0){
				result.put("fail",true);
			//只修改姓名的情况下
			}else if(ulist.size()==0&&plist.size()>0 && elist.size()>0){
				licenseService.modifyLicenseUserName(singlename, singlephone, license.getId());
				result.put("change",true);
			//如果手机号存在，需要把该手机号在用户表里的信息添加到licenseuser表中
			}else if(elist.size()>0){
				//如果该手机号的等级是普通用户表示该用户可以在未付费的情况下被创建
				if(u.getGrade()==0){
					
					LicenseUser licenseUser = new LicenseUser();
					licenseUser.setLicenseId(license.getId());
					licenseUser.setPhone(singlephone);
					licenseUser.setUserId(u.getId());
					licenseUser.setUserName(singlename);
					licenseService.saveLicenseUser(licenseUser);
					
					PropertiesReader pr = PropertiesReader.getInstance();
				    int messageCount=Integer.parseInt(pr.getProperties("messageCount"));
				    
				    License li = licenseService.getLicenseInfoByLicenseId(license.getId());
				    if(li.getOverplusCount()<messageCount){
				    	HttpSend.sendMessage(singlephone, "恭喜您成为天佑网（www.tianyouwang.net）会员，账号：您的手机号，密码：123456");
				    	licenseService.modifyOverPlusCount(li.getOverplusCount()+1, license.getId());
				    }
					
					if(licenseUserId!=null && !licenseUserId.equals("")){
						//根据licenseUserId查询手机号，然后将用户表里的该手机号的等级变成普通用户
						LicenseUser lu = licenseService.getById(licenseUserId);
						accountService.modifyGrade(0, lu.getPhone());
						//将被修改的用户在台帐表里的到期时间改为0
						cardLogService.modifyLastTotalAndOverTotal(0, 0, lu.getUserId());
						//删除licenseUser表里被修改的数据
						licenseService.deleteLicenseUserId(licenseUserId);
					}
					
					if(num != null && num.equals("1")){
						licenseService.modifyUserId(u.getId(), license.getId());
						licenseService.modifyUserGrade(2, u.getId());
						
						PropertiesReader preader = PropertiesReader.getInstance();
					    int tryOutTimes=Integer.parseInt(preader.getProperties("tryOutTimes"));
						
						CardLog cardTry = new CardLog();
						cardTry.setAuthsUserId(u.getId());
						//addcl.setBizCardId(cl.getBizCardId());
						cardTry.setBizType(0);
						cardTry.setDataDate(new Date());
						cardTry.setInsertName("系统充值");
						cardTry.setLastTotal(0);
						cardTry.setValue(0);
						cardTry.setOverTotal(tryOutTimes);
						cardTry.setStatus(0);
						cardLogService.saveCardLog(cardTry);
						//cardTry.setSummary(cl.getSummary());
						
					}else{
						//将除了白金用户以外的其他用户的等级都修改为黄金用户
						licenseService.modifyUserGrade(1, u.getId());
					}
					if(li.getOverplusCount()<messageCount){
						result.put("changephone",true);
					}else if(li.getOverplusCount()>=messageCount){
						result.put("changephoneTip",true);
					}
					
				//如果该手机号不是普通用户，就不能被创建或者修改
				}else{
					result.put("tip", true);
				}
			//如果该手机号不存在，而且不是修改操作，执行下列方法
			}else{
				User user = new User();
				Additional addi = new Additional();
				user.setUsername(singlephone);
				user.setAliasname(singlename);
				user.setPassword("123456");
				user.setUserType(1);
				user.setStatus("1");
				user.setNonLocked(true);
				user.setEnable(true);
				user.setPhone(singlephone);
				user.setFirstLogin("0");
				addi.setId(user.getId());
				addi.setMobile(singlephone);
				user.setAdditional(addi);
				user.getAdditional().setUser(user);
				Set<Role> rs = new TreeSet<Role>();//声明权限集合
				Role r = accountService.getById("5");//取得个人权限
				rs.add(r);//把个人权限放入权限集合中
				user.setRoles(rs);//把权限集合赋给注册的个人用户
				accountService.saveUser(user);
				
				LicenseUser licenseUser = new LicenseUser();
				licenseUser.setLicenseId(license.getId());
				licenseUser.setPhone(singlephone);
				licenseUser.setUserId(user.getId());
				licenseUser.setUserName(singlename);
				licenseService.saveLicenseUser(licenseUser);
				
				PropertiesReader pr = PropertiesReader.getInstance();
			    int messageCount=Integer.parseInt(pr.getProperties("messageCount"));
			    
			    License li = licenseService.getLicenseInfoByLicenseId(license.getId());
			    if(li.getOverplusCount()<messageCount){
			    	HttpSend.sendMessage(singlephone, "恭喜您成为天佑网（www.tianyouwang.net）会员，账号：您的手机号，密码：123456");
			    	licenseService.modifyOverPlusCount(li.getOverplusCount()+1, license.getId());
			    }
				
				//如果当前执行发送的是第一行，更新license表中用户id的值，说明该用户是白金用户
				if(num != null && num.equals("1")){
					licenseService.modifyUserId(user.getId(), license.getId());
					licenseService.modifyUserGrade(2, user.getId());
					
					PropertiesReader preader = PropertiesReader.getInstance();
				    int tryOutTimes=Integer.parseInt(preader.getProperties("tryOutTimes"));
					
					CardLog cardTry = new CardLog();
					cardTry.setAuthsUserId(user.getId());
					//addcl.setBizCardId(cl.getBizCardId());
					cardTry.setBizType(0);
					cardTry.setDataDate(new Date());
					cardTry.setInsertName("系统充值");
					cardTry.setLastTotal(0);
					cardTry.setValue(0);
					cardTry.setOverTotal(tryOutTimes);
					cardTry.setStatus(0);
					cardLogService.saveCardLog(cardTry);
					//cardTry.setSummary(cl.getSummary());
					
				}else{
					licenseService.modifyUserGrade(1, user.getId());
				}
				if(licenseUserId!=null && !licenseUserId.equals("")){
					//根据licenseUserId查询手机号，然后将用户表里的该手机号的等级变成普通用户
					LicenseUser lu = licenseService.getById(licenseUserId);
					accountService.modifyGrade(0, lu.getPhone());
					//将被修改的用户在台帐表里的到期时间改为0
					cardLogService.modifyLastTotalAndOverTotal(0, 0, lu.getUserId());
					//删除licenseUser表里被修改的数据
					licenseService.deleteLicenseUserId(licenseUserId);
				}
				//根据当前登录的license查询信息
				License l = licenseService.getLicenseInfoByLicenseId(license.getId());
				//根据当前登录的license的白金用户的id查询此白金用户在台帐表中的信息
				CardLog cl = cardLogService.getCardInfoByUserId(l.getUserId());
				
				CardLog addcl = new CardLog();
				addcl.setAuthsUserId(user.getId());
				addcl.setBizCardId(cl.getBizCardId());
				addcl.setBizType(cl.getBizType());
				addcl.setDataDate(cl.getDataDate());
				addcl.setInsertName(cl.getInsertName());
				addcl.setLastTotal(cl.getLastTotal());
				addcl.setValue(cl.getValue());
				addcl.setOverTotal(cl.getOverTotal());
				addcl.setStatus(cl.getStatus());
				addcl.setSummary(cl.getSummary());
				//保存一条当前被创建用户的台帐信息
				cardLogService.saveCardLog(addcl);
				//根据天佑卡的id查询此卡的其他信息
				Card card = bizService.getCardById(cl.getBizCardId());
				//如果当前licence创建了一个新的用户，需要将对应的天佑卡的余额减去240元
				bizService.modifyPrice(cl.getBizCardId(),card.getPrice()-240);
				if(li.getOverplusCount()<messageCount){
					result.put("success",true);
				}else if(li.getOverplusCount()>=messageCount){
					result.put("messageTip", true);
				}
				
			}
		}
		//返回结果
		return result;
	}
	
	
	/**
	 * 查询当前许可号是否已存在白金用户
	 * 
	 */
	@RequestMapping(value = "/existLicenseUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> existLicenseUser(HttpServletRequest request,HttpServletResponse response,Model model){
		//定义返回结果
		Map<String,Object> result=new HashMap<String,Object>();
		//获取licenseId
		String licenseId = request.getParameter("licenseId");
		//根据licenseId查询此序列号信息
		License license = licenseService.getLicenseInfoByLicenseId(licenseId);
		//如果次license对应的用户部位空，证明此序列号已经有白金用户
		if(license.getUserId()!= null && !license.getUserId().equals("")){
			result.put("success", true);
		}else{
			result.put("fail", true);
		}
		//返回结果
		return result;
	}
	
	/**
	 * 判断当前许可号是否已付费
	 * 
	 */
	@RequestMapping(value = "/checkPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkPay(HttpServletRequest request,HttpServletResponse response,Model model){
		//定义返回结果
		Map<String,Object> result=new HashMap<String,Object>();
		String licenseId = request.getParameter("licenseId");
		//根据license的id查询当前序列号的信息
		License license = licenseService.getLicenseInfoByLicenseId(licenseId);
		//如果当前的序列号的状态为2，说明此序列号已交费
		if(license.getLicenseStatus()==2){
			result.put("fail", true);
		}
		//返回结果
		return result;
	}
	
	
}
