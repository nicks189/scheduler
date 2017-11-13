$(document).ready(function () {
    $('#datepicker').datepicker({
        dateFormat: 'yy-mm-dd', minDate: 0
    });
    $('#datetimepicker1').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss'
    });
});