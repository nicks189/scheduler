$(document).ready(function () {
    $( "#datetimepicker2" ).datetimepicker({
        format: 'YYYY-MM-DD'
    });
    $('#datetimepicker1').datetimepicker({
        format: 'YYYY-MM-DD hh:mm a'
    });
});