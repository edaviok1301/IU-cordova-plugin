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

exports.getAppPermissionStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getAppPermissionStatus', [arg0]);
}

exports.setAppPermissionStatus = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'setAppPermissionStatus', [arg0]);
}

exports.getCurrentOrganizationName = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'getCurrentOrganizationName', [arg0]);
}

exports.createOrganisationFromCountryCode = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'createOrganisationFromCountryCode', [arg0]);
}

exports.setSensitiveDataSwitch = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'setSensitiveDataSwitch', [arg0]);
}

exports.saveFCMToken = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'saveFCMToken', [arg0]);
}

exports.requestNotificationPermission = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'requestNotificationPermission', [arg0]);
}

exports.addOTAPromotionReceiverListener = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'addOTAPromotionReceiverListener', [arg0]);
}

exports.addNotificationListener = function (arg0, success, error) {
	exec(success, error, 'amlibrary', 'addNotificationListener', [arg0]);
}
