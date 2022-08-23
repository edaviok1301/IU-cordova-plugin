var exec = require('cordova/exec');

exports.launch = function (arg0, success, error) {
    exec(success, error, 'amlibrary', 'launch', [arg0]);
};

exports.onRefreshToken = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'onRefreshToken', [arg0]);
}

exports.onMessageReceived = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'onMessageReceived', [arg0]);
}

exports.setClientAttributes = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'setClientAttributes', [arg0]);
}

exports.getFCMSenderId = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getFCMSenderId', [arg0]);
}

exports.getSDKStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getSDKStatus', [arg0]);
}

exports.trackInAppEvents = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'trackInAppEvents', [arg0]);
}

exports.updateOptInStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'updateOptInStatus', [arg0]);
}

exports.getOptInStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getOptInStatus', [arg0]);
}

exports.updateEngagementOptInStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'updateEngagementOptInStatus', [arg0]);
}

exports.getEngagementOptInStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getEngagementOptInStatus', [arg0]);
}
