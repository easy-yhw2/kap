/*!************************************************************************************
 * jQuery Paging 0.2.1
 * by composite (ukjinplant@msn.com)
 * http://hazard.kr
 * This project licensed under a MIT License.
 **************************************************************************************/;
!function($){
	'use strict';
	
	//default properties.
	var a=/a/i,defs={
		prnt:'li',item:'a',next:'&gt;',prev:'&lt;',format:'{0}',
		itemClass:'button',sideClass:'',className:'',
		itemCurrent:'active',length:10,max:1,current:1,append:false,
		href:'#{0}',event:true,first:'«',last:'»'
	},format=function(str){
		var arg=arguments;
		return str.replace(/\{(\d+)}/g,function(m,d){
			if(+d<0) return m;
			else return arg[+d+1]||"";
		});
	},prnt,item,make=function(op,page,cls,str){
		prnt=document.createElement(op.prnt);
		item=document.createElement(op.item);
		switch(str){
			case op.prev:prnt.className='prev';break;
			case op.next:prnt.className='next';break;
			case op.first:prnt.className='first';break;
			case op.last:prnt.className='last';break;
			default:;break;
		}
		prnt.className=cls;
		item.innerHTML=format(str,page,op.length,op.start,op.end,op.start-1,op.end+1,op.max);
		if(a.test(op.item)) item.href=format(op.href,page);
		if(op.event){
			prnt.appendChild(item);
			$(prnt).bind('click',function(e){
				var fired=true;
				if($.isFunction(op.onclick)) fired=op.onclick.call(item,e,page,op);
				if(fired==undefined||fired)
					$(op.origin).paging({current:page});
				return fired;
			}).appendTo(op.origin);
			//bind event for each elements.
			var ev='on';
			switch(str){
				case op.prev:ev+='prev';break;
				case op.next:ev+='next';break;
				case op.first:ev+='first';break;
				case op.last:ev+='last';break;
				default:ev+='item';break;
			}
			if($.isFunction(op[ev])) op[ev].call(item,page,op);
		}
		return item;
	};

	$.fn.paging=function(op){
		$(this).each(function(){
			if(this.__JQ_PAGING){
				if(op === 'destroy'){
					$(this).removeClass(this.__JQ_PAGING.className).empty();
					delete this.__JQ_PAGING;
					return true;
				}else if(op in this.__JQ_PAGING){
					return this.__JQ_PAGING[op];
				}
				op=$.extend(this.__JQ_PAGING,op||{});$(this).empty();
			}else if(op instanceof String || typeof op === 'string') return false;
			else{
				op=$.extend({origin:this},defs,op||{});
				$(this).addClass(op.className).empty();
			}
			if(op.max<1) op.max=1; if(op.current<1) op.current=1;
			op.start=~~((op.current-1)/op.length)*op.length+1;
			op.end=op.start-1+op.length;
			if(op.end>op.max) op.end=op.max;
			
			if(op.current>op.length){
				if(op.first!==false) make(op,1,op.sideClass,op.first);//first button
				make(op,op.start-1,op.sideClass,op.prev);//prev button
			}else if(op.current == op.end && op.end != 1){
				make(op,1,op.sideClass,op.first);//first button
			}
			//pages button
			for(var i=op.start;i<=op.end;i++)
				make(op,i,op.itemClass+(i==op.current?' '+op.itemCurrent:''),op.format);

			if(op.current/op.length<op.max/op.length){
				if(op.end<op.max) make(op,op.end+1,op.sideClass,op.next);//next button
				if(op.last!==false) make(op,op.max,op.sideClass,op.last);//last button
			}
			this.__JQ_PAGING=op;
		});
	};
}(jQuery);