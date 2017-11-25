import * as ActionTypes from '../constants/ActionTypes';

export function createClient(name) {
    return {
        type: ActionTypes.CREATE_CLIENT,
        payload: {
            name: name
        }
    }
}

export function createClientFulfilled(data) {
    return {
        type: ActionTypes.CREATE_CLIENT_FULFILLED,
        payload: data
    };
}

export function createClientRejected(error) {
    return {
        type: ActionTypes.CREATE_CLIENT_REJECTED,
        payload: error
    };
}

export function createUser(userName, password, role, clientName, clientSecret) {
    return {
        type: ActionTypes.CREATE_USER,
        payload: {
            userName: userName,
            password: password,
            clientName: clientName,
            clientSecret: clientSecret,
            role: role
        }
    }
}

export function createUserFulfilled(data) {
    return {
        type: ActionTypes.CREATE_USER_FULFILLED,
        payload: data
    };
}

export function createUserRejected(error) {
    return {
        type: ActionTypes.CREATE_USER_REJECTED,
        payload: error
    };
}

export function requestTokenByPassword(username, password, clientName, clientSecret) {
    return {
        type: ActionTypes.REQUEST_TOKEN,
        payload: { username: username, password: password, clientName: clientName, clientSecret: clientSecret }
    };
}

export function requestTokenByPasswordFulfilled(data) {
    return {
        type: ActionTypes.REQUEST_TOKEN_FULFILLED,
        payload: data
    };
}

export function requestTokenByPasswordRejected(error) {
    return {
        type: ActionTypes.REQUEST_TOKEN_REJECTED,
        payload: error.response.body
    };
}

export function clearResponses() {
    return {
        type: ActionTypes.CLEAR_AUTH_RESPONSES
    }
}