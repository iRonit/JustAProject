/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function(){
    document.cookie = "uid=";
};


function login() {
    if(validate_ip_addr()) 
        if(validate_uid()) 
            if(validate_pass()){
                document.cookie = "uid="+document.getElementById("uid").value;
                return true;
            }
    return false;
}



function validate_ip_addr() {
        
    var ip_addr = document.getElementById("ip_addr").value;
    var re;
    //--------------RE for IP/Hostname Format
    var ipv6 = /(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))/;
    var ipv4 = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/;
    var hostname = /^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$/;
    //-----------------------------------------
    
    if(get_frequency(ip_addr,".")===3)
        re = ipv4;
    else if(ip_addr.indexOf(':')> -1)
        re = ipv6;
    else
        re = hostname;
    
    if(re.test(ip_addr))
        return true;
    else
    {
        alert("Invalid CUCM Address!");
        return false;
    }
    
}

function validate_uid() {
    if(!validate_ip_addr())
        return false;
    var uid = document.getElementById("uid").value;
    var check = /^[A-Za-z0-9_.]{3,20}$/;
    if(check.test(uid))return true;
    alert("Invalid Username!");
    return false;
}

function validate_pass() {
    var pass = document.getElementById("pass").value;
    if(pass!=="")
        return true;
    alert("Invalid Password!");
    return false;
}

function get_frequency(text,fchar) {
    var count = 0;
    for(var i =0;i<text.length; i++)
        if(text[i]===fchar)
            count++;
    return count;
}
