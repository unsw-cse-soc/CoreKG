import React from "react";
import ReactDOM from "react-dom";
import RenderInput from './ui/RenderInput';
import RenderDropDown from './ui/RenderDropDown';
import RenderSubmitButton from './ui/RenderSubmitButton';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector } from 'redux-form';
import isNil from 'lodash/isNil';
import isEmpty from 'lodash/isEmpty';
import trimValue from '../services/TrimValue';
import { CREATE_DATABASE } from '../constants/ActionTypes';

class DatabaseComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
    }

    render() {
        const { handleCreateDatabaseSubmit, handleDeleteDatabaseSubmit, responses } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Create a database</div>
                <p class="caption">
                    Data API supports two types of databases: Relational database and NoSQL. Although we recommand using NoSQL databases due mainly to higher performance, this decision is related to use case.
                In order to create a database, you need to set its type and name.
                </p>
                <CreateDatabaseForm onSubmit={handleCreateDatabaseSubmit} />
                {responses.has(CREATE_DATABASE) && <ResponseComponent res={responses.get(CREATE_DATABASE)} />}
            </div>
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Delete a database</div>
                <p class="caption">
                    You are able to delete a database using a DELETE request. Please be advised that it is not possible to recover a deleted database.
                </p>
                <DeleteDatabaseForm onSubmit={handleDeleteDatabaseSubmit} />
            </div>
        </div>
    }
}

export default DatabaseComponent;

class CreateDatabaseForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit, submitting, databaseNameValue, databaseTypeValue} = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -H "Authorization: Bearer ACCESS_TOKEN" -X POST -d '{"name":"${(isNil(databaseNameValue) || isEmpty(databaseNameValue)) ? 'Dataset_NAME' : databaseNameValue}", "type": "${(isNil(databaseTypeValue) || isEmpty(databaseTypeValue)) ? 'Database_NAME' : databaseTypeValue}"}' http://CoreKG/api/databases`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <div class="row">
                    <Field name="databaseName"
                        id="database-name-input"
                        label="Name"
                        component={RenderInput}
                        withContainer={false}
                        normalize={trimValue}
                        type="text" />
                    <Field name="databaseType"
                        id="database-type-input"
                        label="Type"
                        component={RenderDropDown}
                        withContainer={false}
                        options={[
                            { value: 'postgresql', text: 'PostgreSQL' },
                            { value: 'mysql', text: 'MySql' },
                            { value: 'oracle', text: 'Oracle' },
                            { value: 'mongodb', text: 'MongoDB' },
                            { value: 'hive', text: 'Hive' },
                            { value: 'hbase', text: 'HBase' }
                        ]} />
                </div>
                <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
            </form>
        </div>
    }
}

CreateDatabaseForm = reduxForm({
    form: 'newDatabaseForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(CreateDatabaseForm);

const databaseFormSelector = formValueSelector('newDatabaseForm');

CreateDatabaseForm = connect(
    state => {
        return {
            databaseNameValue: databaseFormSelector(state, 'databaseName'),
            databaseTypeValue: databaseFormSelector(state, 'databaseType')
        }
    }
)(CreateDatabaseForm);

class DeleteDatabaseForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { handleSubmit, submitting } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" -X DELETE http://CoreKG/api/databases/{DATABASE_NAME}`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <Field name="databaseName"
                    id="database-name-input"
                    label="Name"
                    component={RenderInput}
                    normalize={trimValue}
                    type="text" />
                <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
            </form>
        </div>
    }
}

DeleteDatabaseForm = reduxForm({
    form: 'deleteDatabaseForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(DeleteDatabaseForm);