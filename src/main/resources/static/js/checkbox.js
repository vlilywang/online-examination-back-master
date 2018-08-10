$(function () {
    $('input[name="all"]').change(function () {
        var checked = this.checked ;
        $('input[name="single"]').each(function (index, el) {
            el.checked = checked;
        });
    });
    $("table").delegate('input[name="single"]', 'change', function () {
        var checked = this.checked;
        var flag = true
        $('input[name="single"]').each(function (index, el) {
            if (!el.checked) {
                flag = false;
                return;
            }
        });
        $('input[name="all"]').get(0).checked = flag;
    })
})