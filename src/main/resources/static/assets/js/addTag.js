layui.use(['config', 'element', "laytpl", 'form'], function(){
    var $ = layui.jquery;
    var config = layui.config;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var form = layui.form;
    element.init();
    form.render();

    var studentIdTpl = document.getElementById("student-id").innerHTML;
    var courseCountTpl = document.getElementById("final-score").innerHTML;
    var scoreTpl = document.getElementById("video-percent").innerHTML;
    var videoWatchTpl = document.getElementById("test-percent").innerHTML;
    var passTpl = document.getElementById("pass").innerHTML;

    //$.ajaxSettings.async = true;

    form.on('select(course-id-select)', function (formdata) {
        formdata = formdata.value.split("+").join("%2B");
        $.get(config.base_server + 'tag/getStudentList?access_token=' + config.getToken() + "&courseId=" + formdata, function (data) {
            console.log(data);

            var studentIdView = document.getElementById("student-id-box");
            laytpl(studentIdTpl).render(data, function(html){
                studentIdView.innerHTML = html;
            });
            element.render();
            form.render();
        });
    });

    var courseId;
    var studentId;
    form.on('submit(choose-id)', function (data) {
        console.log(data);
        courseId = data.field.courseId;
        //var courseId = "course-v1:TsinghuaX+60700052X+2019_T1";
        studentId = data.field.studentId;
        //var studentId = 2165;
        courseId = courseId.split("+").join("%2B");
        $.get(config.base_server + 'tag/studentData?access_token=' + config.getToken() + "&courseId=" + courseId + "&studentId=" + studentId, function (data) {
            //console.log(data["final_score"]);
            var final_score = data["final_score"] === undefined? 0: data["final_score"];
            var video_percent = data["video_percent"] === undefined? 0: data["video_percent"];
            var test_percent = data["test_percent"] === undefined? 0: data["test_percent"];
            var final_score_level = data["final_score_level"] === undefined? "F": data["final_score_level"];
            console.log(final_score);

            var courseCountView = document.getElementById("final-score-box");
            laytpl(courseCountTpl).render(final_score.toFixed(2), function(html){
                courseCountView.innerHTML = html;
            });


            var scoreView = document.getElementById("video-percent-box");
            laytpl(scoreTpl).render((video_percent * 100).toFixed(2), function(html){
                scoreView.innerHTML = html;
            });


            var videoWatchView = document.getElementById("test-percent-box");
            laytpl(videoWatchTpl).render((test_percent * 100).toFixed(2), function(html){
                videoWatchView.innerHTML = html;
            });


            var passView = document.getElementById("pass-box");
            laytpl(passTpl).render(final_score_level, function(html){
                passView.innerHTML = html;
            });
            element.render();

            var myChart0 = echarts.init(document.getElementById('video-count'));
            myChart0.showLoading();
            var cols = [];
            for(var i = 1; i <= data["video_watch_times"].length; i ++){
                cols.push("video" + i)
            }
            var option0 = {
                backgroundColor: '#ffffff',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        //data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18'],
                        data: cols,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barWidth: '60%',
                        data: data["video_watch_times"]
                    }
                ]
            };
            myChart0.hideLoading();
            myChart0.setOption(option0);

            var myChart1 = echarts.init(document.getElementById('video-length'));
            myChart1.showLoading();
            cols = [];
            for(i = 1; i <= data["video_watch_time"].length; i ++){
                cols.push("video" + i)
            }
            var option1 = {
                backgroundColor: '#ffffff',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: cols,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barWidth: '60%',
                        data: data["video_watch_time"]
                    }
                ]
            };
            myChart1.hideLoading();
            myChart1.setOption(option1);

            var myChart2 = echarts.init(document.getElementById('video-sequence'));
            myChart2.showLoading();
            cols = [];
            for(i = 1; i <= data["video_watch_history"].length; i ++){
                cols.push("time" + i)
            }
            var option2 = {
                backgroundColor: '#ffffff',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: cols
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        type: 'line',
                        data: data["video_watch_history"]
                    }
                ]
            };
            myChart2.hideLoading();
            myChart2.setOption(option2);

            var myChart3 = echarts.init(document.getElementById('test-score'));
            myChart3.showLoading();
            cols = [];
            for(i = 1; i <= data["test_score"].length; i ++){
                cols.push("test" + i)
            }
            var option3 = {
                backgroundColor: '#ffffff',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: cols
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barGap: 0,
                        data: data["test_score"]
                    }
                ]
            };
            myChart3.hideLoading();
            myChart3.setOption(option3);

            var myChart4 = echarts.init(document.getElementById('test-history'));
            myChart4.showLoading();
            cols = [];
            for(i = 1; i <= data["test_history"].length; i ++){
                cols.push("time" + i)
            }
            var option4 = {
                backgroundColor: '#ffffff',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: cols
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        type: 'line',
                        data: data["test_history"]
                    }
                ]
            };
            myChart4.hideLoading();
            myChart4.setOption(option4);
        });
        return false;
    });

    form.on('submit(add-tag)', function (data) {
        console.log(data.field.tag);
        $.get(config.base_server + 'tag/addTag?access_token=' + config.getToken() + "&courseId=" + courseId + "&studentId=" + studentId + "&tag=" + data.field.tag, function (data) {
            alert(data);
        });
        return false;
    });
});