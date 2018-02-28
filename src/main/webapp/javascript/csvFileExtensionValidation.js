$(document).ready(function(){
    $("#file").change(function(){
        var ext = $('#file').val().split('.').pop().toLowerCase();
     	if($.inArray(ext, ['csv']) == -1) {     		
    		alert('Invalid File.. Please upload CSV File.');
    		$('#file').val('');
            }
    });
});