onload=function(){
	
	$("#popup").mousedown(function(e){
		if(e.target.id=="popup"){
			$("#popup").fadeOut();
		}
	});
	
	$("#write").click(function(){
		//ajax
		
		strDate=$("#hidden").html();
		memo=$("#writeplace").val();
		
	    fetch(`./insert?strDate=${strDate}&memo=${memo}`)
	    .then(function(r){
	      console.log(r.status)
	    })
	    
	    
	    sign=memo.length>7? "..." : memo
		$(`#${strDate}>span>span`).html(sign)
		$(`#${strDate}>div`).html(memo);
	    
		// close
		$("#popup").fadeOut();
	});
}

function over(id){
	memo=$(`#${id}>div`).html();
	$("#memodiv").html(memo);
}

function out(){
	$("#memodiv").html("");
}

function dateclick(id){
	$("#popup").fadeIn();
	memo=$(`#${id}>div`).html();
	$("#writeplace").val(memo);
	$("#hidden").html(id);
}