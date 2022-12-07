import React, { Component } from "react";
import EmployeeService from '../../services/EmployeeService'
import './ListEmployees.css'
import { NavLink } from "react-router-dom";
// import {BiSearch} from 'react-icons/bi'

class ListEmployee extends Component{
    constructor(props) {
        super(props)
        
        this.state = {
            employees: []
        }
        this.editEmployee = this.editEmployee.bind(this);
        this.deleteEmployee = this.deleteEmployee.bind(this)
    }

    deleteEmployee(id){
        EmployeeService.deleteEmployee(id).then(res => {
            this.setState({employees: this.state.employees.filter(employee => employee.id !== id)});
        });
    }

    viewEmployee(id){
        this.props.history.push(`/view-employee/${id}`);
    }

    editEmployee(id){
        this.props.history.push(`/add-employee/${id}`);
    }

    componentDidMount(){
        EmployeeService.getEmployees().then(res => {
            this.setState({employees: res.data});
        });
    }

    render() {
        return(
            <div>
                <h2 className="text-center">LIST OF ALL EMPLOYEES</h2>
                <div className="row row-1">
                    <input type="search" placeholder="Search employee..." className="form-control search"/>
                    <NavLink className="btn btn-primary" to='/add-employee'>Add new employee</NavLink>
                </div>
                <br />
                <div className="row">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Employee First Name</th>
                                <th>Employee Last Name</th>
                                <th>Employee Email</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.employees.map(
                                    employee =>
                                    <tr key={employee.id}>
                                        <td>{employee.firstName}</td>
                                        <td>{employee.lastName}</td>
                                        <td>{employee.email}</td>
                                        <td>
                                            <button onClick={ () => this.editEmployee(employee.id)} className="btn update">Update</button>
                                            <button style={{marginLeft: "10px"}} onClick={() => this.deleteEmployee(employee.id)} className="btn delete">Delete</button>
                                            <button style={{marginLeft: "10px"}} onClick={() => this.viewEmployee(employee.id)} className="btn view">View</button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListEmployee;