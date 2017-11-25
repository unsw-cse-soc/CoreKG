import * as ActionTypes from '../constants/ActionTypes';

export function createRelation(accessToken,
    sourceType,
    destinationType,
    sourceDatabase,
    sourceDataset,
    sourceId,
    destinationDatabase,
    destinationDataset,
    destinationId,
    relationName,
    relationType) {
    return {
        type: ActionTypes.CREATE_RELATION,
        payload: {
            accessToken: accessToken,
            sourceType: sourceType,
            destinationType: destinationType,
            sourceDatabase: sourceDatabase,
            sourceDataset: sourceDataset,
            sourceId: sourceId,
            destinationDatabase: destinationDatabase,
            destinationDataset: destinationDataset,
            destinationId: destinationId,
            relationName: relationName
        }
    }
}

export function createRelationFulfilled(data) {
    return {
        type: ActionTypes.CREATE_RELATION_FULFILLED,
        payload: data
    };
}

export function createRelationRejected(error) {
    return {
        type: ActionTypes.CREATE_RELATION_REJECTED,
        payload: error
    };
}