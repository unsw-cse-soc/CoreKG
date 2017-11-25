import { applyMiddleware, createStore } from "redux";
import logger from "redux-logger";
import { browserHistory } from 'react-router';
import { routerMiddleware } from 'react-router-redux';
import reducer from "./reducers";
import rootSagas from "./sagas/index";
import createSagaMiddleware from 'redux-saga';

const sagaMiddleware = createSagaMiddleware();

const middleware = applyMiddleware(sagaMiddleware, logger(), routerMiddleware(browserHistory));

const store = createStore(reducer, middleware);

sagaMiddleware.run(rootSagas);

export default store;