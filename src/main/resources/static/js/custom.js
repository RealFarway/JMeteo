$(document).ready(function(){
    $('.toast').toast('show');
    $('.toast').click(function(){
        $(this).toast('hide');
    });
});