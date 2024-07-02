import React, {useContext, useEffect} from 'react';
import {useQuery} from "@tanstack/react-query";
import Axios from "axios";
import {Context} from "./dashboard.tsx";

const Metrics: React.FC = () => {
    const {reload}=useContext(Context)

    const getDash=useQuery({
        queryKey:["DASH_DATA"],
        queryFn(){
            return Axios.get("http://localhost:8080/employee/dashboard")
        },enabled:reload
    })

    useEffect(() => {
        console.log(reload)
    }, [reload]);

    return (
        <div className="mb-5">
            <h3 className="text-xl font-bold mb-3">Key Metrics</h3>

            <div className="grid grid-cols-3 gap-4">
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Total Employees</h4>
                    <p className="text-2xl">{getDash?.data?.data?.empCount}</p>
                </div>
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Retention Rate</h4>
                    <p className="text-2xl">{getDash?.data?.data?.retentionCount}%</p>
                </div>
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Average Satisfaction</h4>
                    <p className="text-2xl">{getDash?.data?.data?.jobSatisCount}%</p>
                </div>
            </div>
        </div>
    );
};

export default Metrics;
