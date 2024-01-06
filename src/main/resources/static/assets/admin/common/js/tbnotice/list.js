import * as constants from "/assets/common/js/constants.js";
import {customAjax} from "/assets/common/js/common.js";

export function adminGetTbnoticeList(page){
	
	let sort = $('#select_tbnotice_order').val();
	if(sort == null || sort == undefined || sort == ''){
		sort = 'createdAt,desc';
	}
	
	if(page == undefined || page == null){
		page = 1;
	}
	
	customAjax({
		admin: true,
		url: "/api/tbnotice/list",
		method: "GET",
		data: {"page": (page - 1), "sort": sort},
		success: (data, status, xhr)=>{
			switch(xhr.status){
	    		case constants.HttpStatusCodes.OK:
	    			let content = data.content;
	    			
	    			$('#tbody_tbnotice_list').empty();
	    			
	    			let i = 0;
	    			for(let tbnotice of content){
	    				
	    				let tbnotice_html = '<tr class="gradeA odd" role="row" style="cursor:pointer;" onclick="location.href=\'/admin/tbnotice/detail/'+tbnotice.id+'\'">'
	    							      + '    <td class="sorting_1" style="text-align:center;">'+(++i)+'</td>'
				    					  + '    <td class="sorting_1">'+tbnotice.title+'</td>'
				    					  + '    <td>'+tbnotice.content+'</td>'
				    					  + '    <td>'+tbnotice.createdAt+'</td>'
				    					  + '</tr>'
				    					  ;
	    				
	    				$('#tbody_tbnotice_list').append(tbnotice_html);
	    				
	    			}
	    			
	    			const PAGINATION_SIZE = 5;
	    			let currentPage = data.pageable.pageNumber + 1;
	    			let endPage = Math.ceil(currentPage / PAGINATION_SIZE) * PAGINATION_SIZE;
	    			let startPage = endPage - (PAGINATION_SIZE - 1);
	    			
	    			if(endPage > data.totalPages){
	    				endPage = data.totalPages; 
	    			}
	    			
	    			$('#ul_tbnotice_pagination').empty();
	    			
	    			if(!data.first){
	    				let page_prev_html = '<li class="paginate_button page-item previous">'
					  	  				   + '    <a href="javascript:void(0)" onclick="adminGetTbnoticeList('+(page - 1)+')" aria-controls="DataTables_Table_0" data-dt-idx="'+(currentPage - 1)+'" tabindex="0" class="page-link">Previous</a>'
					  	  				   + '</li>'
					  	  				   ;	
	    				
	    				$('#ul_tbnotice_pagination').append(page_prev_html);
	    			}
	    			
	    			for(let p = startPage; p <= endPage; p++){
	    				let page_html = '<li id="li_page_'+p+'" class="paginate_button page-item">'
								  	  + '    <a href="javascript:void(0)" onclick="adminGetTbnoticeList('+p+')" aria-controls="DataTables_Table_0" data-dt-idx="'+p+'" tabindex="0" class="page-link">'+p+'</a>'
								  	  + '</li>'
								  	  ;
	    				
	    				$('#ul_tbnotice_pagination').append(page_html);
	    			}
	    			
	    			$('#li_page_'+currentPage).addClass('active');
	    			
	    			if(!data.last){
	    				let page_next_html = '<li class="paginate_button page-item next">'
					    				   + '    <a href="javascript:void(0)" onclick="adminGetTbnoticeList('+(page + 1)+')" aria-controls="DataTables_Table_0" data-dt-idx="'+(currentPage + 1)+'" tabindex="0" class="page-link">Next</a>'
					    				   + '</li>'
					    				   ;	
	    				
	    				$('#ul_tbnotice_pagination').append(page_next_html);
	    			}
	    			
	    			break;
	    		default:
	    			console.log("no matching status code");
	    	}
		},
		error: (data)=>{
			console.log('error data : ', data);
		}
	});

}