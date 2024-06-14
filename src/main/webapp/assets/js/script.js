function getUrlParam(name) {
    // 取得url中?后面的字符
    const query = window.location.search.substring(1);
    // 把参数按&拆分成数组
    const param_arr = query.split("&");
    for (let i = 0; i < param_arr.length; i++) {
        const pair = param_arr[i].split("=");
        if (pair[0] == name) {
            return pair[1];
        }
    }
    return '';
}

// 为侧边栏添加链接点击效果
let type = getUrlParam('tab');
if (type == '' || type == null) {
    type = 'details';
}
$('#tab-' + type).click();

// 获得表单数据
function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

/**
 * 提示消息
 * @param text
 * @param icon
 * @param hideAfter
 */
function showMsg(text, icon, hideAfter) {
    const heading = "提示";
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff'
    });
}

function showMsgAndRedirect(text, icon, hideAfter, url) {
    const heading = "提示";
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.href = url;
        }
    });
}


function showMsgAndReload(text, icon, hideAfter) {
    const heading = "提示";
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.reload();
        }
    });
}

/**
 * 登录提交
 */
$('#login_submit').click(function () {
    let prevLink = document.referrer;
    $.ajax({
        type: 'post',
        url: '/login',
        data: $('#login_form').serialize(),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                if ($.trim(prevLink) == '' || $.trim(prevLink) == '/login') {
                    prevLink = '/';
                }
                showMsgAndRedirect(data.msg, "success", 1000, prevLink);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 注册提交
 */
$('#register_submit').click(function () {
    $.ajax({
        type: 'post',
        url: '/register',
        data: $('#register_form').serialize(),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 加载菜单
 */
function loadCategoryList() {
    $.ajax({
        type: 'get',
        url: '/category',
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                let str = '';
                $.each(data.data, function (i, item) {
                    str += '<li><a href="/?cateId=' + item.id + '">' + item.name + '(' + item.count + ')' + '</a></li>'
                });
                $('#categories_ul').html(str);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });

};
loadCategoryList();

/**
 * 删除用户记录
 */
$('.delete_user').click(function () {
    const id = $(this).attr('data-id');
    const tr = $(this).parents('tr');
    $.ajax({
        type: 'delete',
        url: '/account/user/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
                tr.remove();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 更新用户状态
 */
$('.update_user_status').click(function () {
    var a = $(this);
    var text = a.text();
    const id = a.attr('data-id');
    $.ajax({
        type: 'post',
        url: '/account/user/status/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                if(text == '禁用') {
                    a.html('已启用');
                    a.removeClass('text-danger');
                    a.addClass('text-success');
                } else {
                    a.html('禁用');
                    a.removeClass('text-success');
                    a.addClass('text-danger');
                }
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 删除购物车记录
 */
$('.delete_cart').click(function () {
    const id = $(this).attr('data-id');
    const tr = $(this).parents('tr');
    $.ajax({
        type: 'delete',
        url: '/account/cart/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
                tr.remove();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 删除竞价记录
 */
$('.delete_bidding').click(function () {
    const id = $(this).attr('data-id');
    const tr = $(this).parents('tr');
    $.ajax({
        type: 'delete',
        url: '/account/bidding/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
                tr.remove();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 删除商品记录
 */
$('.delete_product').click(function () {
    const id = $(this).attr('data-id');
    const tr = $(this).parents('tr');
    $.ajax({
        type: 'delete',
        url: '/account/product/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
                tr.remove();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 加入购物车记录
 */
$('.add_cart').click(function () {
    const id = $(this).attr('data-id');
    $.ajax({
        type: 'post',
        url: '/cart',
        data: {
            'productId': id
        },
        success: function (data) {
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});


/**
 * 新增竞价
 */
$('.add_bidding').click(function () {
    const id = $(this).attr('data-id');
    let price = $(this).prev('.price').val();
    if (price.toString().trim().length <= 0) {
        showMsg("请输入竞价", "error", 1000);
        return;
    }
    console.log(price)
    console.log(id)
    $.ajax({
        type: 'post',
        url: '/bidding',
        data: {
            'productId': id,
            'price': price
        },
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsgAndReload(data.msg, "success", 2000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

// 加载购物车商品数量和订单数量
function loadCartAndOrderQuantity() {
    $.ajax({
        type: 'get',
        url: '/quantity',
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                $('.cart_quantity').html(data.data.cartTotal);
                $('.wishlist_quantity').html(data.data.biddingTotal);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
}

loadCartAndOrderQuantity();


// 保存用户信息
$('#save_account_submit').click(function () {
    $.ajax({
        type: 'post',
        url: '/account/details',
        contentType: "application/json; charset=utf-8",
        dataType: 'json',//json 返回值类型
        data: JSON.stringify(getFormData($('#user_account_form'))),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

$('#save_password_submit').click(function () {
    $.ajax({
        type: 'post',
        url: '/account/password',
        data: $('#user_password_form').serialize(),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

$('body').on('change', '#file', function () {
    var formData = new FormData();
    var files = $($(this))[0].files[0];
    formData.append("file", files);
    $.ajax({
        url: '/file/upload',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (res) {
            console.log(res);
            $('#imgUrl').val(res.data);
            // alert('上传成功');
        }
        , error: function (res) {
            // alert('错误');
        }
    });
})

/**
 * 发布商品提交
 */
$('#product_submit').click(function () {
    $.ajax({
        type: 'post',
        url: '/account/product',
        data: $('#product_form').serialize(),
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                $('#id').val(data.data);
                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

const datatable_lang = {
    "sProcessing": "处理中...",
    "sLengthMenu": "显示 _MENU_ 项结果",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sInfoPostFix": "",
    "sSearch": "搜索:",
    "sUrl": "",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};

/**
 * 商品列表
 */
$(function () {
    $('#products-table').DataTable({
        language: datatable_lang,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': false,
        'info': true,
        'autoWidth': false
    })
});


/**
 * 竞价列表分页
 */
$(function () {
    $('#biddings-table').DataTable({
        language: datatable_lang,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': false,
        'info': true,
        'autoWidth': false
    })
});

/**
 * 购物车列表分页
 */
$(function () {
    $('#users-table').DataTable({
        language: datatable_lang,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': false,
        'info': true,
        'autoWidth': false
    })
});


/**
 * 购物车列表分页
 */
$(function () {
    $('#carts-table').DataTable({
        language: datatable_lang,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': false,
        'info': true,
        'autoWidth': false
    })
});

/**
 * 订单列表
 */
$(function () {
    $('#orders-table').DataTable({
        language: datatable_lang,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': false,
        'info': true,
        'autoWidth': false
    })
});

$('#productEndTime').fdatepicker({
    format: 'yyyy-mm-dd hh:ii:ss',
    pickTime: true
});


/**
 * 支付
 */
$('.pay_order').click(function () {
    var a = $(this);
    const id = $(this).attr('data-id');
    $.ajax({
        type: 'post',
        url: '/account/order/pay/' + id,
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                a.parents("tr").find('.order_status').html('已支付');
                a.remove();

                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});


/**
 * 发货
 */
$('.send_order').click(function () {
    var a = $(this);

    const id = $(this).attr('data-id');
    $.ajax({
        type: 'post',
        url: '/account/order/send/' + id,
        success: function (data) {
            if (data.code == 0) {
                a.parents("tr").find('.order_status').html('已发货');
                a.remove();

                showMsg(data.msg, "success", 1000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 确认收货
 */
$('.confirm_order').click(function () {
    var a = $(this);
    const id = a.attr('data-id');
    $.ajax({
        type: 'post',
        url: '/account/order/confirm/' + id,
        success: function (data) {
            if (data.code == 0) {
                a.parents("tr").find('.order_status').html('已确认');
                a.remove();
                showMsg(data.msg, "success", 2000);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
