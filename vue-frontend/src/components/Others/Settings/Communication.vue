//Adapted By GRPTeam202205 (MIT license)
<template>
    <!-- Group chatting page -->
    <div class="container py-5">
        <div class="row">
            <div class="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">
                <!-- Show online members -->
                <h5 class="font-weight-bold mb-3 text-center text-lg-start">Members</h5>
                <div class="card">
                    <div class="container">
                    <div class="card-body" v-html="member">
                    </div>
                    </div>
                </div>
            </div>
            <!-- Show chatting messages -->
            <div class="col-md-6 col-lg-7 col-xl-8" style=" height:700px; overflow:auto">
                <ul class="d-flex flex-column" v-html="content">
                </ul>
                <li class="bg-white mb-3">
                    <div class="form-outline">
                        <textarea class="form-control" id="textAreaExample2" rows="4" v-model="this.message"></textarea>
                        <label class="form-label" for="textAreaExample2">{{$t('Message')}}</label>
                    </div>
                </li>
                <button type="button" class="btn btn-info btn-rounded float-end" @click="websocketsend">{{$t('Send')}}</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            userName: '',
            message: '',
            content: '',
            member: '',
            users: [],
            messages: []
        };
    },
    created(){
        this.userName = sessionStorage.getItem('username')
    },
    mounted() {
        this.initWebSocket()
    },
    methods: {
        initWebSocket(){
            const wsuri = 'ws://120.55.194.201:8085/websocket/'+this.userName     
            this.websock = new WebSocket(wsuri)
            this.websock.onmessage = this.websocketonmessage
            this.websock.onopen = this.websocketonopen
            this.websock.onerror = this.websocketonerror
            this.websock.onclose = this.websocketclose
        },
        //Send messages
        websocketsend(){
            let message = {from: this.userName, to: "Tom", text: this.message}
            this.websock.send(JSON.stringify(message))
            this.messages.push({user: this.userName, text: this.message})
            this.addNewContent(null, 'You', this.message)
            this.message = ''
        },
        websocketonopen(){
            console.log("Open WebSocket successfully")
        },
        //Reconnect the websocket if it is not opened successfully
        websocketonerror(){
            this.initWebSocket()
        },
        //Receive messages
        websocketonmessage(msg){
            console.log(msg.data)
            let data = JSON.parse(msg.data)
            if(data.users){//online users
                for(var key in data.users){
                    if(this.users==''){
                        this.addNewMember(data.users[key]['username'])
                    }
                        
                    for(var user in this.users){
                        console.log(this.users[user]['username'])
                        if(data.users[key]['username']!=this.users[user]['username'])
                            this.addNewMember(data.users[key]['username'])
                    }
                }
                this.users = data.users
            }
            else{
                if(data.from!=this.userName)
                    this.addNewContent(data.from, null, data.text)
            }
        },
        //Close the websocket
        websocketclose(){
            this.websock.close()
        },
        //Add chatting content of team members to the chat box
        addNewContent(remoteSender, sender, text){
            let html
            if(sender){
                html =  
                    "<div class=\"card\">\n"+
                    "<div class=\"card-header d-flex justify-content-between p-3\">\n"+
                     "   <p class=\"fw-bold mb-0\">"+sender+"</p>\n"+
                      "  <p class=\"text-muted small mb-0\"><i class=\"far fa-clock\"></i> 12 mins ago</p>\n"+
                    "</div>\n"+
                    "<div class=\"card-body\">\n"+
                     "   <p class=\"mb-0\">"+text+"</p>\n"+
                    "</div>\n"+
                    "</div>"
            }else if(remoteSender){
                    html = 
                            "<div class=\"card w-100\">\n"+
                            "<div class=\"card-header d-flex justify-content-between p-3\">\n"+
                                "<p class=\"fw-bold mb-0\">"+remoteSender+"</p>\n"+
                                "<p class=\"text-muted small mb-0\"><i class=\"far fa-clock\"></i> 13 mins ago</p>\n"+
                            "</div>\n"+
                            "<div class=\"card-body\">\n"+
                            "    <p class=\"mb-0\">\n"+ text + "</p>\n"+
                            "</div>\n"+
                        "</div>\n"
            }
            this.content+=html
        },
        //Add new team members to the chatting list
        addNewMember(name){
            let memberHTML
            memberHTML = 
                    "<li class=\"p-2 border-bottom\" style=\"background-color: #eee;\">\n"+
                        "<a class=\"d-flex justify-content-between\">\n"+
                            "<div class=\"d-flex flex-row\">\n"+
                                "<img src=\"/static/UserProfile/user.png\" alt=\"avatar\" class=\"rounded-circle d-flex align-self-start me-3 shadow-1-strong\" width=\"60\">\n"+
                                "<div class=\"pt-1\">\n"+
                                    "<p class=\"fw-bold mb-0\">"+name+"</p>\n"+
                                    "<p class=\"small text-muted\">Active Mode</p>\n"+
                                "</div>\n"+
                            "</div>\n"+
                            "<div class=\"pt-1\">\n"+
                                "<p class=\"small text-muted mb-1\">Just now</p>\n"+
                            "</div>\n"+
                        "</a>\n"+
                    "</li>\n"
            this.member+=memberHTML
        }
    }
}
</script>

<style>

</style>