/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Disabling direct access to this page
var check = getCookie("uid");
if(check==="")
    window.location.href = "index.html";


window.onload = init;
alert(document.location.host+"\n"+document.location.pathname);
var socket = new WebSocket("ws://localhost:38940/WebCTIConnector/CTIConnector");
socket.onmessage = onMessage;

function onMessage(event) {
    alert("Event: "+event.data);
    var line = JSON.parse(event.data);
    alert("Break point!!\n  "+line.action);

    if (line.action === "add") {
        printDeviceElement(line);
        
    }
    if (line.action === "remove") {
        document.getElementById(line.id).remove();
        //line.parentNode.removeChild(line);
    }
    /*if (line.action === "toggle") {
        var node = document.getElementById(line.id);
        var statusText = node.children[2];
        if (line.status === "On") {
            statusText.innerHTML = "Status: " + line.status + " (<a href=\"#\" OnClick=toggleDevice(" + line.id + ")>Turn off</a>)";
        } else if (line.status === "Off") {
            statusText.innerHTML = "Status: " + line.status + " (<a href=\"#\" OnClick=toggleDevice(" + line.id + ")>Turn on</a>)";
        }
    }*/
           
}

function addLine(name) {
    var LineAction = {
        action: "add",
        name: name,
        status: "idle",
        lines:["123"],
        caller:"ronit",
        called:["1233","4322"]
    };
    socket.send(JSON.stringify(LineAction));
    alert(socket.readyState);
}

function removeDevice(element) {
    var id = element;
    var LineAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(LineAction));
}


function printDeviceElement(line) {
    var content = document.getElementById("content");
    
    var lineDiv = document.createElement("div");
    lineDiv.setAttribute("id", line.id);
    lineDiv.setAttribute("class", "status" + line.status);
    content.appendChild(lineDiv);

    var lineName = document.createElement("span");
    lineName.setAttribute("class", "lineName");
    lineName.innerHTML = line.name;
    lineDiv.appendChild(lineName);

    var lineStatus = document.createElement("span");
    lineStatus.innerHTML = "<b>Status: " + line.status + "</b>";
    lineDiv.appendChild(lineStatus);
    
    
 
    /*
    var lineStatus = document.createElement("span");
    if (line.status === "On") {
        lineStatus.innerHTML = "<b>Status:</b> " + line.status + " (<a href=\"#\" OnClick=toggleDevice(" + line.id + ")>Turn off</a>)";
    } else if (line.status === "Off") {
        lineStatus.innerHTML = "<b>Status:</b> " + line.status + " (<a href=\"#\" OnClick=toggleDevice(" + line.id + ")>Turn on</a>)";
        //lineDiv.setAttribute("class", "line off");
    }
    lineDiv.appendChild(lineStatus);
    */
   
   
   
   
    var lineDevices = document.createElement("span");
    lineDevices.innerHTML = "<b>Devices:</b> " + line.devices;
    lineDiv.appendChild(lineDevices);
    
    
    //--------------------------------------------------------------------------
    var lineCaller = document.createElement("span");
    lineCaller.innerHTML = "<b>Caller:</b> " + line.caller;
    lineDiv.appendChild(lineCaller);
    
    var lineCalled = document.createElement("span");
    lineCalled.innerHTML = "<b>Called:</b> " + line.called;
    lineDiv.appendChild(lineCalled);
    //--------------------------------------------------------------------------
   

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + line.id + ")>Remove line</a>";
    lineDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addLineForm").style.display = '';
}

function hideForm() {
    document.getElementById("addLineForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addLineForm");
    var name = form.elements["line_name"].value;
    hideForm();
    document.getElementById("addLineForm").reset();
    addLine(name);
}

function init() {
    document.querySelector('.content .username').innerHTML = getCookie('uid')+"!";
    hideForm();
}



//Navigation Handling
window.onbeforeunload = function() {
    return "Warning! You will be logged out.";
};

function logOut() {
    window.location.replace("index.html");
}


//Cookie Handling
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)===' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name)=== 0) {
            return c.substring(name.length,c.length);
        }
    }
    return "";
}
function destroyCookie(){
    document.cookie = "uid=";
}
