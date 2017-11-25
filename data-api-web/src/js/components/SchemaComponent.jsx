import React from "react";
import ReactDOM from "react-dom";
import RenderInput from './ui/RenderInput';
import RenderSubmitButton from './ui/RenderSubmitButton';
import RenderDropDown from './ui/RenderDropDown';
import { Field, FieldArray, reduxForm } from 'redux-form';

const renderAttributes = ({ fields, meta: { touched, error } }) => (
    <div class="row">
        {fields.map((attribute, index) =>
            <div class="col s12 m12 l12" key={index}>
                <Field name={`${attribute}.name`}
                    id={`attribute-name-input-${index}`}
                    label="Name"
                    component={RenderInput}
                    withContainer={false}
                    type="text" />
                <Field name={`${attribute}.type`}
                    id={`attribute-type-input-${index}`}
                    label="Type"
                    component={RenderDropDown}
                    withContainer={false}
                    options={[
                        { value: 'string', text: 'String' },
                        { value: 'int', text: 'Integer' },
                        { value: 'date', text: 'Date' }
                    ]} />
            </div>
        )}
        <a class="btn-floating waves-effect waves-light blue" onClick={() => fields.push({})}><i class="material-icons">add</i></a>
    </div>
)

class SchemaComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.handleInitialize();
    }

    handleInitialize() {
        const initData = {
            "attributes": [{ name: '', type: '' }],
        }
        this.props.initialize(initData);
    }

    render() {
        const { handleCreateDatabaseSubmit, handleDeleteDatabaseSubmit } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <p class="caption">
                    Schema enables the system to store each attribute with actual type. This inhances the query.
                </p>
                <div class="row">
                    <div class="col s12 m12 l12">
                        <div class="card blue-grey darken-1">
                            <div class="card-content white-text">
                                <pre>curl -X POST -d --data "{"this is for test"}" http://example.com/path/to/resource --header "Content-Type:application/json"</pre>
                            </div>
                        </div>
                    </div>
                    <form class="col s12 m12 l12" onSubmit={this.props.onSubmit}>
                        <FieldArray name="attributes" component={renderAttributes} />
                        <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
                    </form>
                </div>
            </div>
        </div>
    }
}

export default reduxForm({
    form: 'newSchemaForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(SchemaComponent);