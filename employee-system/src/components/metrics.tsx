import React from 'react';

const Metrics: React.FC = () => {
    return (
        <div className="mb-5">
            <h3 className="text-xl font-bold mb-3">Key Metrics</h3>

            <div className="grid grid-cols-3 gap-4">
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Total Employees</h4>
                    <p className="text-2xl">150</p>
                </div>
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Retention Rate</h4>
                    <p className="text-2xl">85%</p>
                </div>
                <div className="bg-white p-5 rounded shadow">
                    <h4 className="text-lg font-semibold">Average Satisfaction</h4>
                    <p className="text-2xl">7.8</p>
                </div>
            </div>
        </div>
    );
};

export default Metrics;
