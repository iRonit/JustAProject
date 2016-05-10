/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var check = getCookie("uid");
if(check==="")
    window.location.href = "index.html";

window.onload = init;

var socket = new WebSocket("ws://localhost:8080/CiscoCTIConnector/CTIConnector");
socket.onmessage = onMessage;

function onMessage(event) {
    var line = JSON.parse(event.data);
    alert(line.action);
    if (line.action === "add") {
        printLineElement(line);
    }
    if (line.action === "remove") {
        document.getElementById(line.id).remove();
        //line.parentNode.removeChild(line);
    }
}

function addLine(name) {
    var LineAction = {
        action: "add",
        name: name,
        status: "active",
        devices:["123"],
        caller:"ronit",
        called:["1233","4322","456566","456456","456456"]
    };
    socket.send(JSON.stringify(LineAction));
}

function removeLine(element) {
    var id = element;
    var LineAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(LineAction));
}

function printLineElement(line) {
    var content = document.getElementById("content");
    
    var lineDiv = document.createElement("div");
    lineDiv.setAttribute("id", line.id);
    lineDiv.setAttribute("class", "line "+line.status);
    content.appendChild(lineDiv);

    var lineNameDiv = document.createElement("div");
    lineNameDiv.setAttribute("class", "lineName "+line.status);
    lineNameDiv.innerHTML = line.name;
    lineDiv.appendChild(lineNameDiv);
    
    var lineContentDiv = document.createElement("div");
    lineContentDiv.setAttribute("class","lineContent");
    lineDiv.appendChild(lineContentDiv);
    
    var lineStatus = document.createElement("span");
    lineStatus.innerHTML = "<b>Status: " + line.status + "</b>";
    lineContentDiv.appendChild(lineStatus);
    
    
    var lineDevices = document.createElement("span");
    lineDevices.setAttribute("class","devices");
    lineDevices.innerHTML = "<b>Devices:</b> " + line.devices.toString().replace(/"/g,"").replace(/,/g,", ");
    lineContentDiv.appendChild(lineDevices);
    
    
    //--------------------------------------------------------------------------
    if(line.status==="active")
    {
        var lineCaller = document.createElement("span");
        lineCaller.setAttribute("class","caller");
        lineCaller.innerHTML = "<b>Caller:</b> " + line.caller.toString().replace(/"/g,"").replace(/,/g,", ");
        lineContentDiv.appendChild(lineCaller);
    
        var lineCalled = document.createElement("span");
        lineCalled.setAttribute("class","called");
        lineCalled.innerHTML = "<b>Called:</b> " + line.called.toString().replace(/"/g,"").replace(/,/g,", ");
        lineContentDiv.appendChild(lineCalled);
    //--------------------------------------------------------------------------
    }

    var removeLine = document.createElement("span");
    removeLine.setAttribute("class", "removeLine");
    removeLine.innerHTML = "<a href=\"#\" OnClick=removeLine(" + line.id + ")>Remove line</a>";
    lineContentDiv.appendChild(removeLine);
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
    /*var LineAction = {
        action: "onLoginSuccessful"
    };*/
    hideForm();
    //socket.send(JSON.stringify(LineAction));
    alert("INIT");
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


