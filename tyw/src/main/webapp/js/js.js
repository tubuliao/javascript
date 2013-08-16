
$(document).ready(function(e) {			
	t = $('.fix2').offset().top;
	mh = $('.detail').height();
	fh = $('.fix2').height();
	$(window).scroll(function(e){
		s = $(document).scrollTop();	
		if(s > t - 10){
			$('.fix2').css('position','fixed');
			if(s + fh > mh){
				$('.fix2').css('top',mh-s-fh+'px');	
			}				
		}else{
			$('.fix2').css('position','');
		}
	})
});
