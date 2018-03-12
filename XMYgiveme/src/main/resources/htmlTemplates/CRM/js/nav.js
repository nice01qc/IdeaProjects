var setting = {
    view: {
        title:'Dashboard',
        showIcon: showIconForTree,
        fontCss:getFont
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};

var zNodes =[
    { id:1, pId:0, name:"用户管理", open:true},
    { id:11, pId:1, name:"新建用户", open:true},
    { id:12, pId:1, name:"用户列表"},
    { id:13, pId:1, name:"分成调整日志"},
    { id:2, pId:0, name:"app管理", open:true},
    { id:21, pId:2, name:"app审核"},
    { id:3, pId:0, name:"资源管理", open:true},
    { id:31, pId:3, name:"资源位审核定价"},
    { id:4, pId:0, name:"创意管理", open:true},
    { id:41, pId:4, name:"创意审核"},
    { id:42, pId:4, name:"新建创意链接类型"},
    { id:43, pId:4, name:"创意链接类型"},
    { id:5, pId:0, name:"投放管理", open:true},
    { id:51, pId:5, name:"投放管理"},
    { id:6, pId:0, name:"财务管理", open:true},
    { id:61, pId:6, name:"充值"},
    { id:62, pId:6, name:"充值查询"},
    { id:63, pId:6, name:"需求方对账单"},
    { id:64, pId:6, name:"资源方对账单"},
    { id:7, pId:0, name:"日志查询", open:true},
    { id:71, pId:7, name:"操作日志"},
];
function showIconForTree(treeId, treeNode) {
    return !treeNode.isParent;
};
$(document).ready(function(){
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
function getFont(treeId, node) {
    return node.font ? node.font : {'color':'white'};
}

var nameCount = 0, iconCount = 1, color = [0, 0, 0];