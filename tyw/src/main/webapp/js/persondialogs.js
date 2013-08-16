$(function() { 
	 
    $('#chooseArea').dialog({
		autoOpen: false, 
		width: 600,
		dialogClass: "my-dialog",
		modal: true
	}); 
    
    $('#chooseProfession').dialog({
		autoOpen: false, 
		width: 550,
		dialogClass: "my-dialog",
		modal: true
	});
		
    $('#chooseJobNature').dialog({
		autoOpen: false, 
		width: 550,
		dialogClass: "my-dialog",
		modal: true
	});
    
    $('#chooseCompanyNature').dialog({
		autoOpen: false, 
		width: 550,
		dialogClass: "my-dialog",
		modal: true
	});
    
    $("input[name='additional.originalFamilyHome']").focus(function () {
		$('#chooseArea').dialog('open');
    }); 
    
    $("input[name='additional.profession']").click(function () {
		$('#chooseProfession').dialog('open');
    }); 
    
    $("input[name='additional.jobNature']").click(function () {
		$('#chooseJobNature').dialog('open');
    }); 
    
    $("input[name='additional.companyNature']").click(function () {
		$('#chooseCompanyNature').dialog('open');
    }); 
   
});