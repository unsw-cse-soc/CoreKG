import * as ActionTypes from '../constants/ActionTypes';

export function createEntity(accessToken, databaseName, datasetName, entityType, attributes) {
    return {
        type: ActionTypes.CREATE_ENTITY,
        payload: {
            accessToken: accessToken,
            databaseName: databaseName,
            datasetName: datasetName,
            entityType: entityType,
            attributes: attributes
        }
    }
}

export function createEntityFulfilled(data) {
    return {
        type: ActionTypes.CREATE_ENTITY_FULFILLED,
        payload: data
    };
}

export function createEntityRejected(error) {
    return {
        type: ActionTypes.CREATE_ENTITY_REJECTED,
        payload: error
    };
}