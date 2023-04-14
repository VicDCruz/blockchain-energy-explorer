import glob
import logging
import os.path

import matplotlib.pyplot as plt
import matplotlib.ticker as tick
import numpy as np
import pandas as pd
from numpy import ndarray
from pandas import DataFrame

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[
        # logging.FileHandler("debug.log"),
        logging.StreamHandler()
    ]
)


def get_value_from_dataframe(indexes: [int], system_time: DataFrame, measure: str):
    if len(indexes) > 0:
        value = extract_value_from_cell(system_time, indexes)
        logging.debug(f"✅ ({value}) ${measure} found!")
        return value
    else:
        logging.warning(f"⚠️ No {measure} found, default to 0...")
        return 0.0


def extract_value_from_cell(system_time, indexes):
    energy_str = system_time.loc[indexes.pop()]  # Cumulative Processor Energy_0 (Joules)
    joules = energy_str.split(' = ').pop()
    return float(joules)


def plot_energies(energies: ndarray, execution_times: ndarray, title: str):
    logging.info(f"Plotting graph with ({len(energies)}) elements...")
    fig, ax = plt.subplots()
    scatter = ax.scatter(np.arange(1, len(energies) + 1), energies, c=execution_times, s=execution_times ** 2.5,
                         alpha=0.5)
    set_plot_metainformation(ax, title)
    add_tags(ax, energies)
    set_stadistics(ax, energies)
    add_colorbar(ax, execution_times, fig, scatter)
    return ax


def set_plot_metainformation(ax, title):
    ax.set_title(title)
    ax.set_ylabel('Joules')
    ax.set_xlabel('Runtimes')
    ax.xaxis.set_major_locator(tick.MultipleLocator())


def add_tags(ax, energies):
    for i, n in enumerate(energies):
        ax.text(i + 1, n, f"{n:.2f}", va='bottom', ha='center')
        # ax.annotate(f"{n:.2f}", (i + 1, n))


def set_stadistics(ax, energies):
    ax.axhline(y=np.nanmean(energies), color='blue', linestyle='--', linewidth=1.5, label='Avg')  # set mean line


def add_colorbar(ax, execution_times, fig, scatter):
    ticks = np.linspace(np.amin(execution_times), np.amax(execution_times), 10, endpoint=True)
    cbar = fig.colorbar(scatter, ax=ax, ticks=ticks)
    cbar.ax.yaxis.set_major_formatter(tick.FormatStrFormatter('%.2f'))  # change values to 2 decimals
    cbar.ax.set_ylabel('Total Elapsed Time (sec)')  # add title to color bar


def get_information():
    total_joules = []
    total_execution_times = []
    all_files = glob.glob(os.path.join('data', '*.csv'))
    for filepath in all_files:
        csv = pd.read_csv(filepath)
        total_joules.append(get_joules_from_csv(csv['System Time']))
        total_execution_times.append(get_execution_time_from_csv(csv['System Time']))
    return np.asarray(total_joules), np.asarray(total_execution_times)


def get_joules_from_csv(system_time: DataFrame):
    string_to_search = r'Cumulative Processor Energy_0 \(Joules\)'
    indexes = system_time.index[system_time.str.contains(string_to_search)].tolist()
    return get_value_from_dataframe(indexes, system_time, 'Joules')


def get_execution_time_from_csv(system_time: DataFrame):
    string_to_search = r'Total Elapsed Time \(sec\)'
    indexes = system_time.index[system_time.str.contains(string_to_search)].tolist()
    return get_value_from_dataframe(indexes, system_time, 'seconds')


if __name__ == '__main__':
    t_energies, t_exec_times = get_information()
    plot_energies(t_energies, t_exec_times, 'Avg energy consumption (Algorand/Java/No JIT)')
    plt.savefig('scatter-joules-runtime.png', bbox_inches='tight')
