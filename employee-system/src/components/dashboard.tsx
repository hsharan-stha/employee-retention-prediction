import React from 'react';
import Sidebar from "./sidebar.tsx";
import Metrics from "./metrics.tsx";
import EmployeeList from "./employee-list.tsx";


const Dashboard: React.FC = () => {
    return (
        <div className="flex">
            <Sidebar active={'dashboard'}/>
            <div className="w-3/4 p-5">
                <Metrics />
                <EmployeeList />
            </div>
        </div>
    );
};

export default Dashboard;
