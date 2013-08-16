package com.isoftstone.tyw.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.Card;
import com.isoftstone.tyw.entity.biz.CardBiz;
import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.biz.Payment;
import com.isoftstone.tyw.entity.biz.PaymentItem;
import com.isoftstone.tyw.service.BizService;

@Controller
public class CardController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有卡列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cardlist", method = RequestMethod.GET)
	public String paymentlist() {
		return "page/card/cardList";
	}
	
	@RequestMapping(value = "/cardlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String cardNo=request.getParameter("cardNo");
		String discountCode=request.getParameter("discountCode");
		Page<Card> page = bizService.listAllCard(cardNo,discountCode,pageable);
		result.put("total",page.getTotalElements());  
		result.put("rows", page.getContent());  
		return result;
	}
	/**
	 * 添加新卡
	 * @return
	 */

    @RequestMapping(value="/addCard",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addCard";   
    }   
    @RequestMapping(value="/addCard",method=RequestMethod.POST)   
    public String doAdd(Card card,Model model) throws Exception{   
        try {   
        	bizService.saveCard(card);   
            model.addAttribute("resMess", "新增卡成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡失败！");   
            throw e;   
        }   
        return "redirect:/cardlist";    
    } 
	/**
	 * 根据ID删除某卡
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/card/delete/{id}")   
    public String delete(@PathVariable("id") String id,Model model)throws Exception{   
        try {   
        	bizService.deleteCardById(id);   
            model.addAttribute("resMess", "删除成功");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "删除失败");   
            throw e;   
        }   
        return "redirect:/cardlist";//重定向   
    }  
	
	/**
	 * 修改卡信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/card/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("card",bizService.getCardById(id));   
	        return "page/card/updateCard";   
	    }   
	    @RequestMapping(value="/card/update/{id}",method=RequestMethod.POST)   
	    public String doUpdate(@PathVariable("id") String id, Card card,Model model) throws Exception{   
	        try {   
	        	bizService.saveCard(card);   
	            model.addAttribute("resMess", "更新成功！");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            model.addAttribute("resMess", "更新失败！");   
	            throw e;   
	        }   
	        return "redirect:/cardlist";//重定向    
	    }  
	    
	    /**
	     * 前台展示账户信息
		 * 账户信息
		 * @return
		 */

	    @RequestMapping(value="/tocardInfo",method=RequestMethod.GET)   
	    public String tocardInfo(@PageableDefaults(value=10) Pageable pageable,
	    		HttpServletRequest request,Model model) throws Exception{
	    	
	    	HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			String id = user.getId();
			
			String returnInfo =  request.getParameter("returnInfo");
			if(null!=returnInfo&&!"".equals(returnInfo)){
				model.addAttribute("addReturn", returnInfo);//返回提示说明添加成功
			}
			
			String hover =  request.getParameter("hover");
			if(null!=hover&&!"".equals(hover)){
				model.addAttribute("hover", "3");//去生成订单的页面
			}
			
			String beCardNo =  request.getParameter("beCardNo");
			if(null!=beCardNo&&!"".equals(beCardNo)){
				model.addAttribute("beCardNo", "1");//卡号不存在
			}
			
			String beActive =  request.getParameter("beActive");
			if(null!=beActive&&!"".equals(beActive)){
				model.addAttribute("beActive", "1");//卡号已经激活，不可再用。
			}
			
			//取出所有的卡类型信息列表
			List<CardType> cardTypeList = new ArrayList<CardType>();
			cardTypeList = (List<CardType>) bizService.getAllCardType();
			
			//取出已购点卡的信息列表
	    	List<Card> CardList = new ArrayList<Card>();
	    	CardList = (List<Card>)bizService.getCardByCreateId(id);
	    	
	    	//取出订单信息列表
	    	List<Payment> PaymentList = new ArrayList<Payment>();
	    	PaymentList = (List<Payment>)bizService.getPaymentByUserId(id);
	    	
	    	//取出购物车信息列表
	    	List<PaymentItem> PaymentItemList = new ArrayList<PaymentItem>();
	    	PaymentItemList = (List<PaymentItem>)bizService.getPaymentItemByUserIdAndOrderStatus(id, "0");
	    	//算出购物车中的金额
	    	double price =0.0d;
	    	for(PaymentItem pi:PaymentItemList){
	    		price+=(pi.getAmount()*pi.getPrice());
	    	}
	    	
	    	model.addAttribute("cardTypelist", cardTypeList);//把卡类型返回到客户端
	    	model.addAttribute("CardList", CardList);//把已购点卡返回到客户端
	    	model.addAttribute("PaymentList", PaymentList);//把订单列表返回到客户端
	    	model.addAttribute("price", price);//把购物车总金额列表返回到客户端
	    	
	    	model.addAttribute("PaymentItemList", PaymentItemList);//把购物车列表返回到客户端
	        return "page/front/card";   
	    }
	    
	    /**
		 * 前台展示--添加购物车
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping(value="/addShoppingCard",method=RequestMethod.GET)   
		    public String addShoppingCard( 
		    		HttpServletRequest request,Model model) throws Exception{
			 	
		        HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				String userId = user.getId();//取得用户ID
			 
			 	String id=request.getParameter("shoppingId");
			 	String a=request.getParameter("shoppingAmount");
			 	Integer amount =new Integer(a);
		        
				CardType cardT = bizService.getCardTypeById(id);//出去添加对应的筑龙卡
				String title = cardT.getTitle();
				PaymentItem payI = bizService.getPaymentItemByUserIdAndCardTypeAndOrderStatus(userId, cardT,"0");//取出用户已添加此类型的购物数据
				if(null==payI||"".equals(payI)){//如果为空，就添加一条数据
					PaymentItem pi = new PaymentItem();
					pi.setAmount(amount);
					pi.setTitle(title);
					pi.setPrice(cardT.getPrice());
					pi.setOrderStatus("0");//未生成订单状态
					pi.setCardType(cardT);
					pi.setUserId(userId);
					bizService.savePaymentItem(pi);
				}else{//更新此条信息的数量
					Integer oldA = payI.getAmount();//原来的数量
					//Double oldP = payI.getPrice();//原来的金额
					
					Integer nowA = oldA+amount;//添加之后的数量
					 //Double nowP = cardT.getPrice()*nowA;//添加之后的金额
					
					payI.setAmount(nowA);
					//payI.setPrice(nowP);
					
					bizService.savePaymentItem(payI);
					
				}
				//request.setAttribute("returnInfo", "ok"); 
				return "redirect:/tocardInfo?returnInfo=ok";//重定向    
		    }
		 
		 /**
		  * 前台购物车的内容生成订单
		  * @param payment
		  * @param request
		  * @param response
		  * @param model
		  * @return
		  */
			@RequestMapping(value = "/produceOrder" ,method = RequestMethod.GET)
			public String produceOrder(Payment payment,
					HttpServletRequest request ,HttpServletResponse response ,Model model){
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				String userId = user.getId();//取得用户ID
					
				try {
					double payMoney = payment.getPayMoney();//从页面取得生成订单的金额
					String remark = payment.getRemark();//从页面取得订单备注
					//String orderCode = (new Date()).toLocaleString();
					Date d=new Date();//获取时间 
				    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddkkmmss");//转换格式 
				    String orderCode = sdf.format(d).toString();//设置订单编号
					
					Payment pt = new Payment();
					pt.setPayMoney(payMoney);
					pt.setRemark(remark);
					pt.setName(user.getUsername());
					pt.setUserId(userId);
					pt.setOrderCode(orderCode);
					 	
					Payment pat = bizService.saveWebPayment(pt);//生成订单
					
					//取出购物车信息列表 把订单信息放入购物车里的信息里
			    	List<PaymentItem> PaymentItemList = new ArrayList<PaymentItem>();
			    	PaymentItemList = (List<PaymentItem>)bizService.
			    			getPaymentItemByUserIdAndOrderStatus(userId, "0");//取出用户购物车下所有未生成订单的数据
			    	for(PaymentItem p : PaymentItemList){
			    		p.setOrderStatus("1");//更新用户当前购物车里订单的状态为已生成订单
			    		p.setOrderId(pat.getId());//订单ID
			    		bizService.savePaymentItem(p);//更新
			    	}
				 }catch (Exception e) {   
			         e.printStackTrace();   
			    } 
				request.setAttribute("hover", "3"); 
				
				return "forward:/tocardInfo";
			}
			
			/**
			 * 前端个人订单支付方法
			 * @param request
			 * @param response
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "/payMoney" ,method = RequestMethod.GET)
			public String payMoney(HttpServletRequest request ,HttpServletResponse response ,Model model){
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				String userId = user.getId();//取得用户ID
			 
			 	String orderId=request.getParameter("orderId");//取得订单ID
				Card card = null;
				try {
					//取出此订单下购物车信息列表
			    	List<PaymentItem> PaymentItemList = new ArrayList<PaymentItem>();
			    	PaymentItemList = (List<PaymentItem>)bizService.getPaymentItemByOrderIdAndOrderStatus(orderId, "1");//取出用户购物车下所有未生成订单的数据
			    	for(PaymentItem p : PaymentItemList){
			    		//p.setOrderId(orderId);//把用户购物车里的数据都加上订单ID
			    		//p.setOrderStatus("1");//把用户购物车里的数据都变成已生成订单状态
			    		Integer amount = p.getAmount();//取出此条购物车数据的天佑卡的个数
			    		for(int i=0;i<amount;i++){//生成对应的点卡
			    			card = new Card();
			    			card.setCardTypeId(p.getCardType().getId());//保存卡类型ID
			    			card.setCardNo(UUID.randomUUID().toString());
			    			card.setCreateDate(new Date());//点卡的创建日期
			    			card.setStatus(0);//点卡的激活状态--为未激活
			    			card.setCreateId(userId);//创建人
			    			bizService.saveCard(card);//生成点卡
			    		}
			    		bizService.savePaymentItem(p);
			    	}
			    	Payment pt = bizService.getPaymentById(orderId);//通过订单ID得到订单对象
			    	pt.setPayTime(new Date());//设置订单付款日期
			    	bizService.savePayment(pt);//更新订单付款日期
				 }catch (Exception e) {   
			         e.printStackTrace();   
			    } 
				request.setAttribute("hover", "3"); 
				
				return "forward:/tocardInfo";
			}
			
			/**
			 * web----用卡充值：通过卡号查出对应的卡类型，卡类型中对应的有多种服务。对多种服务进行更新
			 * @param cardNo
			 * @param request
			 * @param response
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "/payCard" ,method = RequestMethod.GET)
			public String payCard(@ModelAttribute("card") Card c,HttpServletRequest request,
					HttpServletResponse response,Model model){
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				String userId = user.getId();//取得用户ID
				Card card = null;
				try{
						card = bizService.getCardByCardNo(c.getCardNo());//通过卡号查出卡信息
						if("".equals(card)||null==card){//如果根据卡号没有查出对应的卡信息，说明此卡号不存在
							request.setAttribute("beCardNo", "1");//此卡号不存在
							request.setAttribute("hover", "5"); 
							return "forward:/tocardInfo";
						}
						Date activeTime = card.getActiveTime();//取出激活日期
						if(!"".equals(activeTime)&&null!=activeTime){
							request.setAttribute("beActive", "1");//此卡号已经激活
							request.setAttribute("hover", "5"); 
							return "forward:/tocardInfo";
						}
						String cardTypeId = card.getCardTypeId();//从卡信息里取出卡类型ID
						List<CardBiz> cardBiz = bizService.getCardBizByCardTypeId(cardTypeId);//卡类型对应的服务列表
						/**
						 * 循环服务列表去消费台账里更新记录：
						 * 如果消费台账里此用户有这个类型的卡对应业务消费记录就判断服务时间
						 */
						List<CardLog> cardLogList = null;
						for(CardBiz cb : cardBiz){
							/**
							 * 根据业务，一个业务生成一条台账记录
							 */
							CardLog cl = new CardLog();
							cl.setAuthsUserId(userId);//用户ID
							cl.setBizCardId(card.getId());//卡ID
							cl.setInsertName(userId);//操作人--此用户
							cl.setBizType(cb.getBizType());//业务类型
							cl.setDataDate(new Date());//本次操作日期
							cl.setValue(Integer.parseInt(cb.getValue()));//本次操作的值
							cl.setStatus(1);//此次数据的操作借贷关系为贷，即充值操作为正。
							
							cardLogList = bizService.getCardLogByAuthsUserIdAndBizType(userId, cb.getBizType());//通过用户ID和业务类型得到台账信息
							
							/**
							 * 如果存在此用户的此业务类型有台账记录，就取出此台账信息，在此台账信息的基础之上新增一个此用户和业务类型的台账信息
							 */
							if(cardLogList.size()>0){
								/**
								 * 根据业务类型进行具体操作
								 */
								CardLog lastCardLog = cardLogList.get(cardLogList.size()-1);//取出最后一个台账
								if(cb.getBizType().equals(new Integer(1))){//如果业务类型为1即包年服务
									Date date = lastCardLog.getDataDate();//取得此台账的创建日期
									int over = lastCardLog.getOverTotal();//取得此台账的本期结余
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
									int dateInt = Integer.parseInt(sdf.format(date));//格式化此台账的创建时间
									int nowDate = Integer.parseInt(sdf.format(new Date()));//格式化现在时间
									if(dateInt+over>nowDate){//如果创建日期加上本期结余大于现在的时间的话，说明服务还没到期
										cl.setLastTotal(lastCardLog.getLastTotal());//把上次的本期结余赋值给这次的上次结余
										cl.setOverTotal(dateInt+over-nowDate+Integer.parseInt(cb.getValue()));//上次的本期结余加上本次的操作值为这次的本期结余
									}else{//否则说明服务到期了
										cl.setLastTotal(0);//由于此用户以前没有此业务类型的服务，其上次结余就为0.
										cl.setOverTotal(Integer.parseInt(cb.getValue()));//由于此用户以前没有此业务类型的服务，其本次结余就为这次的操作值。
									}
								}else if(cb.getBizType().equals(new Integer(2))){//如果业务类型为2即买点服务
									cl.setLastTotal(lastCardLog.getLastTotal());//把上次的本期结余赋值给这次的上次结余
									cl.setOverTotal(lastCardLog.getOverTotal()+Integer.parseInt(cb.getValue()));//上次的本期结余加上本次的操作值为这次的本期结余
								}
							}else{//如果不存在此用户的此业务类型有台账记录，就直接新增一个此用户和业务类型的台账信息
								cl.setLastTotal(0);//由于此用户以前没有此业务类型的服务，其上次结余就为0.
								cl.setOverTotal(Integer.parseInt(cb.getValue()));//由于此用户以前没有此业务类型的服务，其本次结余就为这次的操作值。
							}
							
							bizService.saveCardLog(cl);//生成一条台账记录
							card.setActiveTime(new Date());//给此卡一个激活日期
							bizService.saveCard(card);//保存此卡的激活日期
						}
				}catch(Exception e){
					e.printStackTrace();
				}	
				request.setAttribute("hover", "5"); 
				return "forward:/tocardInfo";
			}
		 
		 
}
