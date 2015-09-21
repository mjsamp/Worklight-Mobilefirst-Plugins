

		function runCommand() {
    			this.resultCallback = null; // Function
         	}

		runCommand.prototype.run = function(command) {    
    			cordova.exec(null, null, "RunCommand", "run", [command]);
		};

		cordova.addConstructor(function() {    
    			if(!window.plugins) {
    			window.plugins = {};
    			}

    			// shim to work in 1.5 and 1.6
    			if (!window.Cordova) {
    			window.Cordova = cordova;
    			}

    			window.plugins.runCommand = new runCommand();
    			
    		});
