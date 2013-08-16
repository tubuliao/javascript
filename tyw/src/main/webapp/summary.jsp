<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${tagName eq '土木资讯'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>最新的建筑行业技术、知识信息动态的发布，为工程技术人员提供最新的标准规范颁布等知识信息动态及行业新
         	技术发展应用信息动态等。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '法律法规'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>及时发布国家、建设部、相关部委以及各地建设行政主管部门工程建设相关的法律、法规、文件、通知以及相应的政策解读。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '标准规范'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>系统、全面地发布现行的、最新的工程建设行业涉及的材料标准、建设标准、地方性标准等信息，
         	让工程技术人员随时了解、掌握最新标准规范在工程上的应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '施工图集'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>系统、全面地发布现行的、最新的国家及各地区有关工程建设标准图集信息。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '强制性条文'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>有关工程建设的强制性条文内容的检索查询，移动终端和语音识别功能的应用，使工程技术
         	人员随时随地了解和掌握强制性条文内容，保障工程质量安全。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '规范条文'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>系统、全面提供工程建设常用技术标准规范条文内容的检索查询，移动终端和语音识别功能的应用，使工程技术人员随时
         	随地了解和掌握工程项目施工应用的最新规范条文，确保工程质量和安全。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '安全条文'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>工程建设施工中涉及施工安全的强制性条文和安全规范条文的内容检索查询，移动终端和语音识别功能的应用，使工程技术人员
         	随时随地了解和掌握工程项目施工安全规范条文，确保施工安全工作落实。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '施工工法'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>包括了国家级、各省市级、大型企业等近年来的建筑工程相关工法。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工艺标准'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>来自于多家大型工程建设施工企业的各分部分项工程的施工工艺标准。为工程技术人员提供很好的参考。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '投标施组'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>提供各种结构类型的工程项目投标施组实例。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '实施性施组'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>包括了各种结构类型、不同用途的的工程项目实施性施工组织设计实例。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '施工方案'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>提供大量先进、最新的，包括工程施工组织设计、分项工程施工方案、专项施工方案、施工技术交底等技术文件案例，
         	给工程技术人员工作提供参考与应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '技术交底'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>针对各分部分项工程、不同建筑材料、重点工序和特殊施工做法，提供了多种的施工技术交底。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '施工做法'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>按照分部分项工程，分别给出了工程施工前准备、工程做法、相关技术问答、应注意的事项等多种类型的知识。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '经验技巧'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>大量的来自工程施工一线的、实用的经验技巧和工程做法、案例，给工程技术人员现场实际施工中以很好的学习与应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '基础数据'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>为工程建设施工以及技术人员提供了施工、技术、质量、安全等各项的基础数据。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程案例'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>内容包括了工程管理、质量、安全等案例，通过工程实例，为不同工程施工管理等工作提供很好的参考。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '施工计算'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>针对于分部分项工程的施工过程中常用计算公式、计算实例、计算数据提供了丰富的知识内容，为工程施工技术人员工程中实用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '构造详图'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于分部分项工程以及各构造节点，提供大量的各种构造节点做法详图和CAD图块，直接方便工程技术人员工程施工中应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '质量检验'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>把各分部分项工程质量验收的具体要求、各省市对于工程建设质量验收要求内容，按照知识切片内容形式展现，能方便快捷地查找并实现语音播报功能，
         	方便工程现场质量检查验收工作应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '质量控制'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>按照分部分项工程和施工工序，加强对于工程施工过程的质量控制，
         	确立工序质量控制点，抓好重点、难点、关键点的质量控制工作，确保工程施工质量。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '质量通病'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>按照分部分项工程和施工工序，重点做好对于工程施工过程中常见质量问题、质量通病、
         	质量事故的预防和处理，确保工程施工质量。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程创优'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>根据国家级、省市级建筑、结构优质工程评审要求，对优质精品工程质量策划、管理、
         	施工做法、申报评审以及相应的工程案例等全方位的知识进行整理，以为创优质工程提供参考学习。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程资料'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>工程施工资料表格是建筑工程成果的表现，是作为城建档案的重要组成部分。
         	资料管理模块为工程技术人员提供了各省市相应的资料标准表格、表格填写说明、样表等，
         	对规范工程资料管理工作有着重要的作用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '新规范表格'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>国家、行业每年都有很多的技术标准规范修订、更新和新颁布实施，如何更好地做好这些技术标准规范的落实和执行，
         	资料表格既是工程建设的成果，也是贯彻落实标准规范要求的必要。中国建筑业协会工程建设质量监督分会组织专家每年编制相应的
        	新规范表格，是贯彻执行新技术标准和加强工程质量监督检测的一项重要工作。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '检测机构用表'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>把工程质量检测检验机构对于工程材料试验、施工试验、结构性能检测过程中应用到的检测试验记录用表
         	等，按照检测试验的类别，分别提供了表式和相关说明。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '管理常用表'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于工程施工管理工作常用的表格，按照项目部各部门的设置和工作内容要求，提供了相应的管理工作常用表格。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '安全管理'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>提供了丰富的安全管理制度、安全教育培训、安全检查验收等安全和管理的知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '安全技术'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>按照分部分项工程以及工程现场施工中重点的涉及安全的事项，提供了丰富的安全技术知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '安全资料'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>建筑工程施工现场安全资料表格及相关的方案、交底等文件，虽然不作为城建档案归档，但是有效
         	地保证工程建设施工安全管理工作落实到位，确保人民生命财产安全的一项重要的安全管理措施，当前只有部分省份有系统的施工
         	安全管理资料，因此，做好施工安全管理资料的系统化工作尤为重要。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '环境保护'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>主要包括分部分项工程施工过程中的环境保护和控制要求以及涉及节能减排等内容的。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程管理'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>按照工程项目管理规范的要求，对于工程项目管理过程中，涉及的相关管理工作知识、
         	管理案例以及各项管理工作要求的知识，提供了丰富的有很强参考性内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '招标投标'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于工程招标投标的相关基础知识、相关要求以及招标和投标过程中的文件编制要求，
         	同时提供了大量的投标招标案例供参考学习。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程造价'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>分别按照工程量计算、工程量清单计价、工程概算、工程预算以及工程竣工结算等要求，对于基础知识、
         	基本要求、计量计价规则和要求等等提供了详细的知识内容，并提供大量的工程造价实例供参考学习使用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程物资'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于工程项目施工中常用的建筑材料、设备、构配件等工程物资的相关技术标准要求的内容以及工程物资管理工作相关的要求等，
         	以丰富的内容提供给工程技术人员应用。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '机械机具'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>主要包括了工程施工机械机具的相关技术要求、机械机具选型、安全操作使用、维修保养以及机械机具的管理要求等知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '检测试验'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于工程项目施工中常用的建筑材料、设备、构配件等工程物资、工程施工检测试验、结构性能检测试验以及管理工作要求等，
         	提供了丰富的知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程测量'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>包括了建筑工程施工测量的基础知识、工程施工测量、城市测量、变形测量、内业等各项测量工作提供了专业的知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '培训课程'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于工程建设相关的基础理论培训、工程技术人员岗位及管理培训、各项新技术培训、法律法规以及标准规范的宣贯培训、工程质
         	量培训、资料管理培训和工种培训等各项培训课程提供了相应的课程培训知识要点、培训教材、PPT课程件、视频等资源。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '执业考试'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>本模块内容不仅包括了国家与工程建设相关的各类执业注册考试培训的相关视频、学习内容外，还有工程技术人员岗位教育、继续教育、
         	新技术培训、法规规范宣贯培训等知识内容，对于提升工程技术人员专业技术水平和职业技能有很大的实用性。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '视频资源'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>视频资源既涵盖了工程建设过程中各项具体工作的内容，具体包括各分部分项工程施工做法、相关培训课程视频、工程创优视频、技术人员及工种岗位工作视频等。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '照片图片'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>主要包括工程现场管理、工程施工、工程质量及创优等工程施工过程的照片图片，工程实际效果照片图片等。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '文化论文'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>建筑文化内容广泛，为工程技术人员提供丰富的建筑施工文化知识和有关常识。建筑论文主要为各类工程技术专业论文、文集。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '学生专区'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>为在校的土木工程专业学生学习和即将的实习就业工作，提供大量的、理论与实践相结合，尽快促进学以致用的专业知
         	识内容，为他们能快速成长为合格的工程技术人员提供学习和应用帮助。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '新技术信息'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>包括建筑、装饰、设备安装以及工程机械机具等各类工程建设相关的新产品信息及应用知识内容。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '新技术应用'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>包括住房和城乡建设部十项重点新技术以及各项绿色节能技术在建筑工程中的应用要点和具体的施工
         	做法等知识，对于促进建筑科技进步和技术推广应用提供帮助。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '工程示范'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>对于四新技术在工程项目施工中的应用管理、示范工程实例以及工程的申报和评审工作要求等，提供相关的管理和施工案例。
         </p>
    </div>
</c:if>

<c:if test="${tagName eq '专利技术'}">
	<div class="pindao_info">
       	<h3>${tagName}频道介绍</h3>
         <p>汇集了大量的与建筑工程相关的材料、施工工艺做法等专利技术信息。
         </p>
    </div>
</c:if>


