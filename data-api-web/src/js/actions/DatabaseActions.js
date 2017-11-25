import * as ActionTypes from '../constants/ActionTypes';

export function createDatabase(accessToken, name, databaseType) {
    return {
        type: ActionTypes.CREATE_DATABASE,
        payload: {
            accessToken: accessToken,
            name: name,
            databaseType: databaseType,
        }
    }
}

export function createDatabaseFulfilled(data) {
    return {
        type: ActionTypes.CREATE_DATABASE_FULFILLED,
        payload: data
    };
}

export function createDatabaseRejected(error) {
    return {
        type: ActionTypes.CREATE_DATABASE_REJECTED,
        payload: error
    };
}

export function deleteDatabase(accessToken, name) {
    return {
        type: ActionTypes.DELETE_DATABASE,
        payload: {
            accessToken: accessToken,
            name: name
        }
    }
}

export function deleteDatabaseFulfilled(data) {
    return {
        type: ActionTypes.DELETE_DATABASE_FULFILLED,
        payload: data
    };
}

export function deleteDatabaseRejected(error) {
    return {
        type: ActionTypes.DELETE_DATABASE_REJECTED,
        payload: error
    };
}