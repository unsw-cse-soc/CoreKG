import isNil from 'lodash/isNil';
import Immutable from 'immutable';
import Database from '../models/Database';
import DatabaseMap from '../models/DatabaseMap';
import Response from '../models/Response';
import ResponseMap from '../models/ResponseMap';
import * as ActionTypes from '../constants/ActionTypes';

export default function reducer(state = Immutable.Map({
    fetching: false,
    fetched: false,
    error: null,
}), action) {
    switch (action.type) {
        case ActionTypes.CREATE_ENTITY: {
            return state.withMutations(map => {
                map.set('fetching', true)
                    .set('fetched', false)
                    .set('error', null);
            });
        }
        case ActionTypes.CREATE_ENTITY_FULFILLED: {
            return state.withMutations(map => {
                map.set('fetching', false)
                    .set('fetched', true)
            });

        }
    }
    return state;
}