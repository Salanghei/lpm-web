layui.use(['config', 'element', "laytpl", 'form'], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var form = layui.form;
    element.init();
    form.render();

    $.ajaxSettings.async = true;

    // 获取个人信息
    console.log(config.base_server + 'student/message?access_token=' + config.getToken());
    $.get(config.base_server + 'student/message?access_token=' + config.getToken(), function (data) {
        //console.log(data);
        var getMessageTpl = message.innerHTML;
        var getMessageView = document.getElementById("message-box");
        laytpl(getMessageTpl).render(data, function (html) {
            getMessageView.innerHTML = html;
            element.render();
        });
    });

    // 获取总体学习数据
    $.get(config.base_server + 'history/total?access_token=' + config.getToken(), function(data){
        var courseCount = "0";
        var score = "0";
        var videoWatchRatio = "0";
        var passRatio = "0";
        if(data.courseCount !== "0") {
            //console.log(data);
            courseCount = data.courseCount;
            score = data.score;
            videoWatchRatio = data.videoWatchRatio;
            passRatio = data.passRatio;
        }

        var courseCountTpl = document.getElementById("course-count").innerHTML;
        var courseCountView = document.getElementById("course-count-box");
        laytpl(courseCountTpl).render(courseCount, function(html){
            courseCountView.innerHTML = html;
        });

        var scoreTpl = document.getElementById("score").innerHTML;
        var scoreView = document.getElementById("score-box");
        laytpl(scoreTpl).render(score, function(html){
            scoreView.innerHTML = html;
        });

        var videoWatchTpl = document.getElementById("video-watch").innerHTML;
        var videoWatchView = document.getElementById("video-watch-box");
        laytpl(videoWatchTpl).render(videoWatchRatio, function(html){
            videoWatchView.innerHTML = html;
        });

        var passTpl = document.getElementById("pass-ratio").innerHTML;
        var passView = document.getElementById("pass-ratio-box");
        laytpl(passTpl).render(passRatio, function(html){
            passView.innerHTML = html;
        });
        element.render();
    });

    // 获取学习历史
    $.get(config.base_server + 'history/timeline?access_token=' + config.getToken(), function(data){
        //console.log(data);
        var timelineTpl = document.getElementById("time-line").innerHTML;
        var timelineView = document.getElementById("time-line-box");
        laytpl(timelineTpl).render(data, function(html){
            timelineView.innerHTML = html;
            element.render();
        });
    });

    // 获取已选课程信息
    $.get(config.base_server + 'history/course?access_token=' + config.getToken(), function(data){
        //console.log(data);
        var courseTpl = document.getElementById("choose-course").innerHTML;
        var courseView = document.getElementById("choose-course-box");
        laytpl(courseTpl).render(data, function(html){
            courseView.innerHTML = html;
            element.render();
        });
    })

    /*var myChart = echarts.init(document.getElementById('showStudyCondition'));
    myChart.showLoading();
    var option = {
        backgroundColor: '#ffffff',
        title: {
            text: '成绩分布情况'
        },
        tooltip: {},
        toolbox: {
            show : false
        },
        calculable : true,
        legend: {
            x: 'left',
            y: 'bottom',
            data: ['成绩']
        },
        radar : [
            {
                indicator : [
                    {text: "第一单元测试", max: 100},
                    {text: "第二单元测试", max: 100},
                    {text: "第三单元测试", max: 100},
                    {text: "第四单元测试", max: 100},
                    {text: "第五单元测试", max: 100}
                ]
            }
        ],
        series: [
            {
                type: 'radar',
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data: [
                    {
                        value: [83, 42, 78, 76, 79],
                        name: '成绩'
                    }
                ]
            }
        ]
    };
    myChart.hideLoading();
    myChart.setOption(option);*/
});