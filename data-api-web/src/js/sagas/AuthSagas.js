import * as ActionTypes from '../constants/ActionTypes';
import { take, put, call } from 'redux-saga/effects';
import * as Api from '../services/Apis';
import * as AuthAction from '../actions/AuthActions';
import { normalize } from 'normalizr';
import schema from '../schemas';

export function* handlePostClientRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a create client request
            const {payload} = yield take(ActionTypes.CREATE_CLIENT);
            // call the api
            const data = yield call(Api.Post, '/api/clients', {
                name: payload.name
            });
            // call the success
            yield put(AuthAction.createClientFulfilled(normalize(data.body, schema.client)));
        } catch (e) {
            // call the error
            yield put(AuthAction.createClientRejected(e));
        }
    }
}

export function* handlePostUserRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a create user request
            const {payload} = yield take(ActionTypes.CREATE_USER);
            // call the api
            const data = yield call(Api.Post, '/api/account', {
                userName: payload.userName,
                password: payload.password,
                role: payload.role,
                clientName: payload.clientName,
                clientSecret: payload.clientSecret,
            });
            // call the success
            yield put(AuthAction.createUserFulfilled(normalize(data.body, schema.user)));
        } catch (e) {
            // call the error
            yield put(AuthAction.createUserRejected(e));
        }
    }
}

export function* handleLoginRequest() {
    // run the daemon
    while (true) {
        try {
            // wait for a login request
            const {payload} = yield take(ActionTypes.REQUEST_TOKEN);
            // call the api
            const data = yield call(Api.Form, '/api/oauth/token', {
                grant_type: "password",
                client_id: payload.clientName,
                client_secret: payload.clientSecret,
                username: payload.username,
                password: payload.password
            });
            // call the success
            yield put(AuthAction.requestTokenByPasswordFulfilled(data.body));
        } catch (e) {
            // call the error
            yield put(AuthAction.requestTokenByPasswordRejected(e));
        }
    }
}