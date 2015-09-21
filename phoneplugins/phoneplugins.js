
function PhoneDialer() {
    this.resultCallback = null; // Function
};

PhoneDialer.prototype.dial = function(phoneNmber) {    
	cordova.exec(null, null, "Caller", "call", [phoneNmber]);
};

cordova.addConstructor(function() {    
    	if(!window.plugins) {
    	window.plugins = {};
    	}

    	// shim to work in 1.5 and 1.6
    	if (!window.Cordova) {
    	window.Cordova = cordova;
    	}

    	window.plugins.phoneDialer = new PhoneDialer();
    	//alert("Before dial");
    });
    





function ContactAddUtil() {
    this.resultCallback = null; // Function
};

ContactAddUtil.prototype.add = function(phoneNumber){    
    cordova.exec(null, null, "CallLog", "viewcontact", [phoneNumber]);
};

cordova.addConstructor(function()  {    
    if(!window.plugins)
    {
    window.plugins = {};
    };

    // shim to work in 1.5 and 1.6
    if (!window.Cordova) {
    window.Cordova = cordova;
    };

    window.plugins.contactAdd = new ContactAddUtil();
    //alert("Before dial");
    });



function PickContact() {
    this.resultCallback = null; // Function
};

PickContact.prototype.chooseContact = function(callback){    
    cordova.exec(callback, function(err) {
        console.log(err);
    }, "PickContact", "chooseContact", []);
};


cordova.addConstructor(function() {
    if(!window.plugins) 
    {
    window.plugins = {};	
    };
	
    if (!window.Cordova) {
        window.Cordova = cordova;
    };

    window.plugins.PickContact = new PickContact();
});



function CallLog() {
	this.resultCallback = null; // Function
}


CallLog.prototype.pick = function(callback){    
    cordova.exec(callback, function(err) {
        console.log(err);
    }, "CallLog", "list", []);
};


cordova.addConstructor(function()  {

   if(!window.plugins)
    {
    window.plugins = {};
    }

    // shim to work in 1.5 and 1.6
    if (!window.Cordova) {
    window.Cordova = cordova;
    };
    
    window.plugins.calllog = new CallLog();
    //alert("Before dial");
    });




function PhoneCall() {
	this.resultCallback = null; // Function
}


PhoneCall.prototype.trap = function(callback){    
    cordova.exec(callback, function(err) {
        console.log(err);
    }, "PhoneCallTrap", "startListen", []);
};


PhoneCall.prototype.destroy = function(callback){    
    cordova.exec(callback, function(err) {
        console.log(err);
    }, "PhoneCallTrap", "stopListen", []);
};

cordova.addConstructor(function()  {

   if(!window.plugins)
    {
    window.plugins = {};
    }

    // shim to work in 1.5 and 1.6
    if (!window.Cordova) {
    window.Cordova = cordova;
    };
    
    window.plugins.phonecall = new PhoneCall();
    //alert("Before dial");
    });
