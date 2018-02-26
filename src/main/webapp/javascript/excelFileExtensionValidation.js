$(document).ready(function(){
    $("#file").change(function(){
        var ext = $('#file').val().split('.').pop().toLowerCase();
     	if($.inArray(ext, ['xls','xlsx']) == -1) {     		
    		alert('Invalid File.. Please upload Excel File.');
    		$('#file').val('');
            }
    });
});