		function BackgroundMode() {
    			this.resultCallback = null; // Function
         	}
         	
         	BackgroundMode.prototype._defaults = {
		    title:  'Smart Dialer is running in background',
		    text:   'touch to bring it to foreground.',
		    ticker: 'App is running in background',
		    resume: true,
		    silent: false
		};
		
		BackgroundMode.prototype.getDefaults = function () {
		    return this._defaults;
		};
		
		BackgroundMode.prototype.setDefaults = function () {
		    var defaults = this.getDefaults();

			cordova.exec(null, null, 'BackgroundMode', 'configure', [defaults, true]);
		};

		BackgroundMode.prototype.enable = function () {
		    this._isEnabled = true;
		    cordova.exec(null, null, 'BackgroundMode', 'enable', []);
		};
		
		BackgroundMode.prototype.disable = function () {
		    this._isEnabled = false;
		    cordova.exec(null, null, 'BackgroundMode', 'disable', []);
		};
		
		
		BackgroundMode.prototype.onactivate = function () {};
		
		BackgroundMode.prototype.ondeactivate = function () {};
		
		BackgroundMode.prototype.onfailure = function () {};
		

		cordova.addConstructor(function() {    
    			if(!window.plugins) {
    			window.plugins = {};
    			}

    			// shim to work in 1.5 and 1.6
    			if (!window.Cordova) {
    			window.Cordova = cordova;
    			}

    			window.plugins.BackgroundMode = new BackgroundMode();
    			
    		});
